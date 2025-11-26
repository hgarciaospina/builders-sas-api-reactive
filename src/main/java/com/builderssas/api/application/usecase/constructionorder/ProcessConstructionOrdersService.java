package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
import com.builderssas.api.domain.port.in.constructionorder.ProcessConstructionOrdersUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProcessConstructionOrdersService implements ProcessConstructionOrdersUseCase {

    private final ConstructionOrderRepositoryPort orderRepo;

    // ================================================================
    //                    MORNING CRON
    //            PENDING → IN_PROGRESS si inicia HOY
    // ================================================================
    @Override
    public Mono<Void> processMorningOrders() {

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        return orderRepo.findAll()
                .filter(order ->
                        order.orderStatus() == OrderStatus.PENDING &&
                                order.scheduledStartDate().isEqual(today)
                )
                .map(order ->
                        new ConstructionOrderRecord(
                                order.id(),
                                order.constructionRequestId(),
                                order.projectId(),
                                order.constructionTypeId(),
                                order.requestedByUserId(),
                                order.latitude(),
                                order.longitude(),
                                order.requestedDate(),
                                order.scheduledStartDate(),
                                order.scheduledEndDate(),
                                OrderStatus.IN_PROGRESS,
                                order.createdAt(),
                                now,
                                order.observations(),
                                order.active()
                        )
                )
                .flatMap(orderRepo::save)
                .then();
    }

    // ================================================================
    //                    NIGHT CRON
    //            IN_PROGRESS → FINISHED si termina HOY
    // ================================================================
    @Override
    public Mono<Void> processNightOrders() {

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        return orderRepo.findAll()
                .filter(order ->
                        order.orderStatus() == OrderStatus.IN_PROGRESS &&
                                order.scheduledEndDate().isEqual(today)
                )
                .map(order ->
                        new ConstructionOrderRecord(
                                order.id(),
                                order.constructionRequestId(),
                                order.projectId(),
                                order.constructionTypeId(),
                                order.requestedByUserId(),
                                order.latitude(),
                                order.longitude(),
                                order.requestedDate(),
                                order.scheduledStartDate(),
                                order.scheduledEndDate(),
                                OrderStatus.FINISHED,
                                order.createdAt(),
                                now,
                                order.observations(),
                                order.active()
                        )
                )
                .flatMap(orderRepo::save)
                .then();
    }
}
