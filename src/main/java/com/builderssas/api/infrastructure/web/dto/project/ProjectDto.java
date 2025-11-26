package com.builderssas.api.infrastructure.web.dto.project;

import com.builderssas.api.domain.model.enums.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectDto(
        Long id,
        String name,
        String code,
        String description,
        LocalDate startDate,
        LocalDateTime endDate,
        Integer progressPercentage,
        ProjectStatus status,
        String observations,
        String createdByUserName,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
