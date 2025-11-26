package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.application.util.ProjectCodeGenerator;
import com.builderssas.api.domain.model.enums.ProjectStatus;
import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.infrastructure.web.dto.project.CreateProjectDto;
import com.builderssas.api.infrastructure.web.dto.project.UpdateProjectDto;
import com.builderssas.api.infrastructure.web.dto.project.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public final class ProjectWebMapper {

    private final ProjectCodeGenerator projectCodeGenerator;

    // ======================================================
    // CREATE DTO → RECORD
    // ======================================================
    public Mono<ProjectRecord> toDomain(CreateProjectDto dto, Long createdByUserId, Mono<Long> nextSeq) {
        String normalizedName = dto.name().trim().toLowerCase();
        return ProjectCodeGenerator.generate(dto.name(), nextSeq)
                .map(code -> new ProjectRecord(
                        null,
                        normalizedName,
                        code,
                        dto.description(),
                        dto.startDate(),
                        null,
                        0,
                        ProjectStatus.NOT_STARTED,
                        dto.observations(),
                        createdByUserId,
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ));
    }

    // ======================================================
    // UPDATE DTO → RECORD
    // ======================================================
    public Mono<ProjectRecord> toDomain(UpdateProjectDto dto, ProjectRecord existing, Mono<Long> nextSeq) {
        String normalizedName = dto.name().trim().toLowerCase();

        Mono<String> finalCode =
                Mono.just(normalizedName)
                        .filter(newName -> !newName.equals(existing.name()))
                        .flatMap(newName -> ProjectCodeGenerator.generate(newName, nextSeq))
                        .switchIfEmpty(Mono.just(existing.code()));

        return finalCode.map(code -> new ProjectRecord(
                existing.id(),
                dto.name(),
                code,
                dto.description(),
                existing.startDate(),
                existing.endDate(),
                existing.progressPercentage(),
                existing.status(),
                dto.observations(),
                existing.createdByUserId(),
                existing.active(),
                existing.createdAt(),
                LocalDateTime.now()
        ));
    }

    // ======================================================
    // RECORD → DTO
    // ======================================================
    public ProjectDto toDto(ProjectRecord p, String createdByUserName) {
        return new ProjectDto(
                p.id(),
                p.name(),
                p.code(),
                p.description(),
                p.startDate(),
                p.endDate(),
                p.progressPercentage(),
                p.status(),
                p.observations(),
                createdByUserName,
                p.active(),
                p.createdAt(),
                p.updatedAt()
        );
    }
}
