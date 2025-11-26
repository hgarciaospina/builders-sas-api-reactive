package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.port.in.project.ProcessProjectsDailyUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Nightly cron for processing construction orders.
 * Runs at 23:00 every day.
 *
 * Fully reactive and non-blocking.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectDailyCronService {

    private final ProcessProjectsDailyUseCase processProjectsDailyUseCase;

    @Scheduled(cron = "0 0 23 * * *")
    public void run() {

        processProjectsDailyUseCase
                .processDailyProjects()

                .doOnError(e ->
                        log.error("Nightly cron failed", e)
                )

                .onErrorResume(e -> Mono.empty())

                .subscribe();
    }
}
