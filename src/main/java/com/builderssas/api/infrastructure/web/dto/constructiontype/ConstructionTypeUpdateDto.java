package com.builderssas.api.infrastructure.web.dto.constructiontype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO utilizado para actualizar un tipo de construcción existente.
 *
 * No modifica el estado lógico (active). Ese comportamiento se
 * gestiona únicamente mediante el caso de uso de borrado lógico.
 */
public record ConstructionTypeUpdateDto(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "Días estimados es obligatorio")
        @Positive(message = "Días estimamdos debe ser un número positivo")
        Integer estimatedDays

) {}
