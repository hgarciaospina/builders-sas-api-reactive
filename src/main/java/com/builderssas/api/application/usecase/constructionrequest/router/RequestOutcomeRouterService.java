package com.builderssas.api.application.usecase.constructionrequest.router;

import com.builderssas.api.application.usecase.constructionrequest.builder.RequestStatusBuilderService;
import com.builderssas.api.application.usecase.constructionrequest.notification.NotificationFlowService;
import com.builderssas.api.application.usecase.constructionrequest.order.OrderCreationService;
import com.builderssas.api.application.usecase.constructionrequest.stock.StockValidationService;
import com.builderssas.api.application.usecase.constructionrequest.inventory.InventoryConsumptionService;
import com.builderssas.api.application.usecase.constructionrequest.persistence.RequestPersistenceService;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.RequestStatus;
import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Routes the final outcome of a construction request:
 *
 *  • If REJECTED → persist and notify.
 *  • If APPROVED → create order, consume inventory, persist, notify.
 *
 * Responsibilities:
 *  • Reduce the Orchestrator to a minimal pipeline.
 *  • Encapsulate the branching logic of APPROVED vs REJECTED.
 *
 * Characteristics:
 *  • Pure application flow.
 *  • No loading, no validation, no text templates.
 *  • Fully reactive, no imperative branching.
 */
@Service
@RequiredArgsConstructor
public class RequestOutcomeRouterService {

    private final RequestStatusBuilderService statusBuilder;
    private final RequestPersistenceService persistenceService;
    private final OrderCreationService orderCreationService;
    private final InventoryConsumptionService inventoryConsumptionService;
    private final NotificationFlowService notificationFlowService;

    /**
     * Routes APPROVED or REJECTED flow.
     */
    public Mono<ConstructionRequestRecord> routeOutcome(
            ConstructionRequestRecord original,
            ProjectRecord project,
            ConstructionTypeRecord type,
            boolean coordinatesOK,
            StockValidationService.StockSummary stock
    ) {

        // APPROVED FLOW
        Mono<ConstructionRequestRecord> approvedFlow =
                Mono.just(statusBuilder.buildApproved(original, project, type, stock))
                        .flatMap(persistenceService::save)
                        .flatMap(saved ->
                                orderCreationService.createOrder(saved, stock)
                                        .flatMap(order ->
                                                inventoryConsumptionService.consume(saved, order.id(), stock)
                                                        .then(notificationFlowService.notifyApproved(saved, order))
                                                        .thenReturn(saved)
                                        )
                        );

        // REJECTED FLOW
        Mono<ConstructionRequestRecord> rejectedFlow =
                Mono.just(statusBuilder.buildRejected(original, project, type, coordinatesOK, stock))
                        .flatMap(persistenceService::save)
                        .flatMap(saved ->
                                notificationFlowService.notifyRejected(saved)
                                        .thenReturn(saved)
                        );

        return Mono.just(original.requestStatus())
                .filter(status -> status == RequestStatus.APPROVED)
                .flatMap(ignored -> approvedFlow)
                .switchIfEmpty(rejectedFlow);
    }
}
