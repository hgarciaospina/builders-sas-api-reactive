package com.builderssas.api.infrastructure.web.controller.test;

import com.builderssas.api.domain.port.in.constructionorder.ProcessConstructionOrdersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test/orders")
@RequiredArgsConstructor
public class TestOrderStateController {

    private final ProcessConstructionOrdersUseCase useCase;

    /**
     * Simula el cron de la mañana:
     * PENDING → IN_PROGRESS
     */
    @PostMapping("/simulate-morning")
    public Mono<Void> simulateMorning() {
        return useCase.processMorningOrders();
    }

    /**
     * Simula el cron de la noche:
     * IN_PROGRESS → FINISHED
     */
    @PostMapping("/simulate-night")
    public Mono<Void> simulateNight() {
        return useCase.processNightOrders();
    }

    /**
     * Simula el ciclo completo:
     * PENDING → IN_PROGRESS → FINISHED
     */
    @PostMapping("/simulate-full")
    public Mono<Void> simulateFull() {
        return useCase.processMorningOrders();
    }


}
