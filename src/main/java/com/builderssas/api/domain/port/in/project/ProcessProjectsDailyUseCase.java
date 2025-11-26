package com.builderssas.api.domain.port.in.project;

import reactor.core.publisher.Mono;

/**
 * Use case triggered by the daily project cron.
 * Should execute all domain logic required at 23:00 every day.
 */
public interface ProcessProjectsDailyUseCase {

    /**
     * Processes project updates required by the system.
     *
     * @return Mono<Void> once processing is complete.
     */
    Mono<Void> processDailyProjects();
}
