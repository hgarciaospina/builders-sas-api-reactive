package com.builderssas.api.infrastructure.web.controller.cron;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/cron")
@RequiredArgsConstructor
public class CronController {

    private final ConstructionOrderRepositoryPort orderRepo;

    @PostMapping("/simulate")
    public Mono<String> simulate() {

        Flux<String> morning =
                orderRepo.findAll()
                        .filter(o -> o.orderStatus() == OrderStatus.PENDING)
                        .flatMap(o -> {

                            ConstructionOrderRecord updated =
                                    new ConstructionOrderRecord(
                                            o.id(),
                                            o.constructionRequestId(),
                                            o.projectId(),
                                            o.constructionTypeId(),
                                            o.requestedByUserId(),
                                            o.latitude(),
                                            o.longitude(),
                                            o.requestedDate(),
                                            o.scheduledStartDate(),
                                            o.scheduledEndDate(),
                                            OrderStatus.IN_PROGRESS,
                                            o.createdAt(),
                                            LocalDateTime.now(),
                                            o.observations(),
                                            o.active()
                                    );

                            return orderRepo.save(updated)
                                    .map(s -> "Orden " + s.id() + " PENDING → IN_PROGRESS");
                        });

        Flux<String> night =
                orderRepo.findAll()
                        .filter(o -> o.orderStatus()== OrderStatus.IN_PROGRESS)
                        .flatMap(o -> {

                            ConstructionOrderRecord updated =
                                    new ConstructionOrderRecord(
                                            o.id(),
                                            o.constructionRequestId(),
                                            o.projectId(),
                                            o.constructionTypeId(),
                                            o.requestedByUserId(),
                                            o.latitude(),
                                            o.longitude(),
                                            o.requestedDate(),
                                            o.scheduledStartDate(),
                                            o.scheduledEndDate(),
                                            OrderStatus.FINISHED,
                                            o.createdAt(),
                                            LocalDateTime.now(),
                                            o.observations(),
                                            o.active()
                                    );

                            return orderRepo.save(updated)
                                    .map(s -> "Orden " + s.id() + " IN_PROGRESS → FINISHED");
                        });

        return morning
                .concatWith(night)
                .collectList()
                .map(list ->
                        String.join("\n", list)
                )
                .defaultIfEmpty("No hubo cambios.");
    }
}
