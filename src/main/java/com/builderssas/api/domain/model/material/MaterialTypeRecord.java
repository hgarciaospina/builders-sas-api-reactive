package com.builderssas.api.domain.model.material;

public record MaterialTypeRecord(
        Long id,
        String name,
        String unit,
        Boolean active
) {}
