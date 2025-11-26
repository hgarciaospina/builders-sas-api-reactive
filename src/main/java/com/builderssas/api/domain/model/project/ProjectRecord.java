package com.builderssas.api.domain.model.project;

import com.builderssas.api.domain.model.enums.ProjectStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectRecord(
        Long id,
        String name,
        String code,
        String description,
        LocalDate startDate,
        LocalDateTime endDate,
        Integer progressPercentage,
        ProjectStatus status,
        String observations,
        Long createdByUserId,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
