package com.builderssas.api.infrastructure.web.controller.project;

import com.builderssas.api.domain.port.in.project.ProcessProjectsDailyUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Manual trigger for the nightly project processing cron.
 *
 * Purpose:
 *  • Allows QA/testing to execute the cron logic at any time.
 *  • Does NOT replace the scheduled execution.
 *  • Purely reactive and non-blocking.
 *
 * Security:
 *  • Should be protected or exposed only in internal environments.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/projects/cron")
@RequiredArgsConstructor
public class ProjectCronTestController {

    private final ProcessProjectsDailyUseCase processProjectsDailyUseCase;

    /**
     * Executes the nightly cron logic manually.
     *
     * @return Mono<String> with a confirmation message.
     */
    @PostMapping("/run-daily")
    public Mono<String> runCronManually() {

        log.info("🔥 [CRON TEST] Manual execution triggered: /run-daily");

        return processProjectsDailyUseCase
                .processDailyProjects()
                .doOnSuccess(v -> log.info("✅ [CRON TEST] Nightly project cron executed successfully."))
                .doOnError(e -> log.error("❌ [CRON TEST] Cron execution failed", e))
                .thenReturn("Nightly project cron executed successfully.");
    }
}
