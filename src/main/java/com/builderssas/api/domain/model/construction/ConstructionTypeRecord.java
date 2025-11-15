package com.builderssas.api.domain.model.construction;

public record ConstructionTypeRecord(
        Long id,
        String name,
        Integer estimatedDays,
        Boolean active
) {}
