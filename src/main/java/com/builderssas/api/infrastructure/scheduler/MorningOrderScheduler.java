package com.builderssas.api.infrastructure.scheduler;

import com.builderssas.api.domain.port.in.constructionorder.ProcessConstructionOrdersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MorningOrderScheduler {

    private final ProcessConstructionOrdersUseCase useCase;

    // Ejecuta cada día a las 6 AM
    @Scheduled(cron = "0 0 6 * * *")
    public void run() {
        useCase.processMorningOrders().subscribe();
    }
}
