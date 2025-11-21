package com.builderssas.api.infrastructure.web.dto.constructiontypematerial;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO usado para la actualización de ConstructionTypeMaterial.
 * El id viene dentro del body (regla general del proyecto).
 */
public record ConstructionTypeMaterialUpdateDto(

        @NotNull(message = "id es obligatorio.")
        @Positive(message = "id debe ser un número positivo.")
        Long id,

        @NotNull(message = "quantity es obligatoria.")
        @Positive(message = "quantity debe ser un número positivo.")
        Double quantity

) {}
