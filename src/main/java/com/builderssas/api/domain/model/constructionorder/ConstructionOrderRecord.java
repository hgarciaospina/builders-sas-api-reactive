package com.builderssas.api.domain.model.constructionorder;

import java.time.LocalDate;
import com.builderssas.api.domain.model.enums.OrderStatus;

public record ConstructionOrderRecord(
        Long id,
        Long requestId,
        Long projectId,
        Long constructionTypeId,
        Long createdByUserId,
        Double latitude,
        Double longitude,
        Integer estimatedDays,
        LocalDate startDate,
        LocalDate endDate,
        OrderStatus status,
        Boolean active
) {}
