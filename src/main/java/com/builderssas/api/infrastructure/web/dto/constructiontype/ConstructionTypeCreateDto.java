package com.builderssas.api.infrastructure.web.dto.constructiontype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO utilizado para recibir los datos necesarios para crear un
 * tipo de construcción desde la capa web.
 *
 * Este objeto no contiene lógica; únicamente aplica validaciones
 * básicas sobre los campos proporcionados por el cliente.
 */
public record ConstructionTypeCreateDto(

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "Dias estimados es obligatorio")
        @Positive(message = "Días estimados debe ser un número positivo")
        Integer estimatedDays

) {}
