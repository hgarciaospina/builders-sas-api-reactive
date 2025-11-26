package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.application.usecase.constructionrequest.builder.RequestStatusBuilderService;
import com.builderssas.api.application.usecase.constructionrequest.coordinates.CoordinateValidationService;
import com.builderssas.api.application.usecase.constructionrequest.loader.RequestContextLoaderService;
import com.builderssas.api.application.usecase.constructionrequest.order.OrderCreationService;
import com.builderssas.api.application.usecase.constructionrequest.router.RequestOutcomeRouterService;
import com.builderssas.api.application.usecase.constructionrequest.stock.StockValidationService;
import com.builderssas.api.application.usecase.constructionrequest.stock.StockValidationService.StockSummary;
import com.builderssas.api.application.usecase.constructionrequest.inventory.InventoryConsumptionService;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;

import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class ConstructionRequestOrchestratorService {

    //Services Orchestrator

    private final RequestContextLoaderService contextLoader;
    private final CoordinateValidationService coordinateValidation;
    private final StockValidationService stockValidation;
    private final RequestStatusBuilderService statusBuilder;
    private final OrderCreationService orderCreationService;
    private final InventoryConsumptionService inventoryConsumptionService;
    private final RequestOutcomeRouterService outcomeRouter;

    private final ConstructionRequestRepositoryPort requestRepo;

    private record ValidationBundle(
            boolean coordinatesOK,
            StockSummary stockSummary
    ) {}


    // ============================================================
    //                     MAIN ENTRYPOINT
    // ============================================================

    public Mono<ConstructionRequestRecord> create(ConstructionRequestRecord cmd) {

        return contextLoader.load(cmd)

                .flatMap(ctx ->
                        coordinateValidation.validate(cmd.latitude(), cmd.longitude())
                                .flatMap(coordinatesOK ->
                                        stockValidation.validate(ctx.type().id())
                                                .map(stockSummary ->
                                                        new ValidationBundle(coordinatesOK, stockSummary)
                                                )
                                )
                                .map(bundle ->
                                        new PipelineData(
                                                cmd,
                                                ctx,
                                                bundle.coordinatesOK(),
                                                bundle.stockSummary()
                                        )
                                )
                )


                // Build APPROVED or REJECTED
                .flatMap(data ->
                        Mono.just(data)
                                .filter(d -> d.coordinatesOK && d.stock.allOK())
                                .map(d ->
                                        statusBuilder.buildApproved(
                                                d.original,
                                                d.context.project(),
                                                d.context.type(),
                                                d.stock
                                        )
                                )
                                .switchIfEmpty(
                                        Mono.just(
                                                statusBuilder.buildRejected(
                                                        data.original,
                                                        data.context.project(),
                                                        data.context.type(),
                                                        data.coordinatesOK,
                                                        data.stock
                                                )
                                        )
                                )
                                // Wrap result back into PipelineData
                                .map(result ->
                                        new PipelineData(
                                                result,
                                                data.context,
                                                data.coordinatesOK,
                                                data.stock
                                        )
                                )
                )

                // Persist
                .flatMap(data ->
                        requestRepo.save(data.original)
                                .map(saved -> new PipelineData(
                                        saved,
                                        data.context,
                                        data.coordinatesOK,
                                        data.stock
                                ))
                )

                // Delegate outcome
                .flatMap(data ->
                        outcomeRouter.routeOutcome(
                                data.original,
                                data.context.project(),
                                data.context.type(),
                                data.coordinatesOK,
                                data.stock
                        ).thenReturn(data.original)
                );
    }


    // ============================================================
    //                   INTERNAL PIPELINE DATA
    // ============================================================

    private record PipelineData(
            ConstructionRequestRecord original,
            RequestContextLoaderService.LoadedContext context,
            boolean coordinatesOK,
            StockSummary stock
    ) {}
}