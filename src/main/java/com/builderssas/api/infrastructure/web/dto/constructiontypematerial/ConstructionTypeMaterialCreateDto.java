package com.builderssas.api.infrastructure.web.dto.constructiontypematerial;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO usado para la creación de registros ConstructionTypeMaterial.
 * Forma parte estricta de la capa Web.
 */
public record ConstructionTypeMaterialCreateDto(

        @NotNull(message = "constructionTypeId es obligatorio.")
        @Positive(message = "constructionTypeId debe ser un número positivo.")
        Long constructionTypeId,

        @NotNull(message = "materialTypeId es obligatorio.")
        @Positive(message = "materialTypeId debe ser un número positivo.")
        Long materialTypeId,

        @NotNull(message = "quantity es obligatoria.")
        @Positive(message = "quantity debe ser un número positivo.")
        Double quantity

) {}
