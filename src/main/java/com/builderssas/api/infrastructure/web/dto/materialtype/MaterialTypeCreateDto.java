package com.builderssas.api.infrastructure.web.dto.materialtype;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO usado para recibir la información necesaria para crear
 * un nuevo tipo de material desde la capa web.
 *
 * No contiene lógica de negocio.
 */
public record MaterialTypeCreateDto(

        @NotBlank(message = "El código del material es obligatorio.")
        String code,

        @NotBlank(message = "El nombre del material es obligatorio.")
        String name,

        @NotBlank(message = "La unidad de medida es obligatoria.")
        String unitOfMeasure
) {}
