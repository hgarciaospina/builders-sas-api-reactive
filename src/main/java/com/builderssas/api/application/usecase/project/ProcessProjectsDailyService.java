package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.ProcessProjectsDailyUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Nightly batch processor for project progress and end-date calculation.
 *
 * Responsibilities:
 *  • Load all active projects.
 *  • Load each project's construction orders.
 *  • Compute progressPercentage as:
 *        (finishedOrders / totalOrders) * 100
 *  • Compute endDate as the latest scheduledEndDate among active orders.
 *  • Persist the updated project state.
 *
 * Characteristics:
 *  • Fully reactive and non-blocking (WebFlux).
 *  • Pure application logic; no side effects beyond persistence.
 *  • No imperative constructs (no if/else/ternary/mutation).
 */
@Service
@RequiredArgsConstructor
public class ProcessProjectsDailyService implements ProcessProjectsDailyUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final ConstructionOrderRepositoryPort orderRepo;

    /**
     * Entry point for the nightly batch.
     * Processes all active projects sequentially in a reactive flow.
     */
    @Override
    public Mono<Void> processDailyProjects() {

        return projectRepo.findAll()
                .filter(project -> Boolean.TRUE.equals(project.active()))
                .flatMap(this::recalculateProject)
                .flatMap(projectRepo::save)
                .then();
    }

    /**
     * Recalculates:
     *  • progressPercentage
     *  • endDate
     *
     * This is done by loading all active construction orders for the project
     * and deriving the new fields reactively.
     */
    private Mono<ProjectRecord> recalculateProject(ProjectRecord project) {

        return orderRepo.findByProjectId(project.id())
                .filter(ConstructionOrderRecord::active)
                .collectList()
                .flatMap(orders -> {

                    // ===============================
                    // 1) Pure functional progress calc
                    // ===============================
                    Integer progress =
                            Optional.of(orders.size())
                                    .filter(total -> total > 0)
                                    .map(total ->
                                            (int) Math.round(
                                                    orders.stream()
                                                            .filter(o -> o.orderStatus() == OrderStatus.FINISHED)
                                                            .count()
                                                            * 100.0 / total
                                            )
                                    )
                                    .orElse(0);

                    // ===============================
                    // 2) Pure functional endDate calc
                    // ===============================
                    LocalDateTime endDate =
                            orders.stream()
                                    .map(ConstructionOrderRecord::scheduledEndDate)   // LocalDate
                                    .filter(Objects::nonNull)
                                    .max(Comparator.naturalOrder())
                                    .map(LocalDate::atStartOfDay)                    // LocalDateTime
                                    .orElse(project.endDate());                      // keep existing if none

                    // ===============================
                    // 3) Wrap result in Mono
                    // ===============================
                    return Mono.just(
                            new ProjectRecord(
                                    project.id(),
                                    project.name(),
                                    project.code(),
                                    project.description(),
                                    project.startDate(),
                                    endDate,
                                    progress,
                                    project.status(),
                                    project.observations(),
                                    project.createdByUserId(),
                                    project.active(),
                                    project.createdAt(),
                                    LocalDateTime.now()
                            )
                    );
                });
    }

    /**
     * Internal immutable helper object to improve readability.
     * Avoids tuple.getT1/getT2 anti-patterns.
     */
    private record ProjectCalculation(
            Integer progress,
            LocalDateTime endDate
    ) {}
}
