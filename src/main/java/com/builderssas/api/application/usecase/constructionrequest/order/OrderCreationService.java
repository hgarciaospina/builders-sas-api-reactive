package com.builderssas.api.application.usecase.constructionrequest.order;

import com.builderssas.api.application.usecase.constructionrequest.stock.StockValidationService;
import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;

import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Creates construction orders for APPROVED requests.
 *
 * Responsibilities:
 *  • Compute start/end dates.
 *  • Build order observations.
 *  • Persist the new order.
 *
 * Fully reactive, immutable, application-layer only.
 */
@Service
@RequiredArgsConstructor
public class OrderCreationService {

    private final ConstructionOrderRepositoryPort orderRepo;
    private final ConstructionTypeRepositoryPort typeRepo;

    // ============================================================
    //                    TEXT TEMPLATES (STATIC)
    // ============================================================

    private static final String ORDER_OBSERVATION_TEMPLATE = """
            Orden generada automáticamente desde solicitud aprobada #%d

            Materiales requeridos:
            %s
            """;

    // ============================================================
    //                    PUBLIC API
    // ============================================================

    public Mono<ConstructionOrderRecord> createOrder(
            ConstructionRequestRecord req,
            StockValidationService.StockSummary stock
    ) {

        Mono<LocalDate> startMono =
                orderRepo.findLastByProjectId(req.projectId())
                        .map(last -> last.scheduledEndDate().plusDays(1))
                        .switchIfEmpty(Mono.just(req.requestDate().plusDays(1)));

        return startMono.flatMap(start ->

                typeRepo.findById(req.constructionTypeId())
                        .map(ConstructionTypeRecord::estimatedDays)
                        .defaultIfEmpty(1)
                        .flatMap(days -> {

                            LocalDate end = start.plusDays(days);

                            String materials =
                                    stock.items().stream()
                                            .map(i -> "- " + i.materialName() + ": " + i.required() + " unidades")
                                            .reduce("", (a, b) -> a + "\n" + b);

                            String obs =
                                    ORDER_OBSERVATION_TEMPLATE.formatted(req.id(), materials);

                            ConstructionOrderRecord order =
                                    new ConstructionOrderRecord(
                                            null,
                                            req.id(),
                                            req.projectId(),
                                            req.constructionTypeId(),
                                            req.requestedByUserId(),
                                            req.latitude(),
                                            req.longitude(),
                                            req.requestDate(),
                                            start,
                                            end,
                                            OrderStatus.PENDING,
                                            LocalDateTime.now(),
                                            LocalDateTime.now(),
                                            obs,
                                            true
                                    );

                            return orderRepo.save(order);
                        })
        );
    }
}
