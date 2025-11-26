package com.builderssas.api.infrastructure.scheduler;

import com.builderssas.api.domain.port.in.constructionorder.ProcessConstructionOrdersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NightOrderScheduler {

    private final ProcessConstructionOrdersUseCase useCase;

    // Ejecuta cada día a las 9 PM
    @Scheduled(cron = "0 0 21 * * *")
    public void run() {
        useCase.processNightOrders().subscribe();
    }
}
