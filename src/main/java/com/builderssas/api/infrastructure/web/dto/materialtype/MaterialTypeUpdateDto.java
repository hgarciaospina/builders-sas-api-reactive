package com.builderssas.api.infrastructure.web.dto.materialtype;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para actualizar tipos de material.
 */
public record MaterialTypeUpdateDto(

        @NotBlank(message = "El código del material es obligatorio.")
        String code,

        @NotBlank(message = "El nombre del material es obligatorio.")
        String name,

        @NotBlank(message = "La unidad de medida es obligatoria.")
        String unitOfMeasure,

        Boolean active
) {}
