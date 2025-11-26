package com.builderssas.api.infrastructure.web.dto.constructionrequest;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO utilizado para recibir los datos necesarios para la creación de una
 * ConstructionRequest desde la capa web.
 *
 * Incluye validaciones destinadas a asegurar que los valores proporcionados
 * cumplan con los requisitos mínimos antes de ser enviados al dominio.
 *
 * Este DTO representa únicamente los datos de entrada proporcionados por el
 * cliente y no expone valores generados automáticamente por el sistema.
 */
public record ConstructionRequestCreateDto(

        @NotNull(message = "projectId es obligatorio")
        @Positive(message = "projectId debe ser un número positivo")
        Long projectId,

        @NotNull(message = "constructionTypeId es obligatorio")
        @Positive(message = "constructionTypeId debe ser un número positivo")
        Long constructionTypeId,

        @NotNull(message = "latitude es obligatoria")
        @DecimalMin(value = "-90.0", message = "latitude no puede ser menor que -90.0")
        @DecimalMax(value = "90.0", message = "latitude no puede ser mayor que 90.0")
        Double latitude,

        @NotNull(message = "longitude es obligatoria")
        @DecimalMin(value = "-180.0", message = "longitude no puede ser menor que -180.0")
        @DecimalMax(value = "180.0", message = "longitude no puede ser mayor que 180.0")
        Double longitude

) {}
