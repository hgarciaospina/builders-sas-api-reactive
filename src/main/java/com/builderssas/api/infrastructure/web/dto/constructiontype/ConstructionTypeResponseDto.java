package com.builderssas.api.infrastructure.web.dto.constructiontype;

import java.time.LocalDate;

/**
 * DTO utilizado para exponer un tipo de construcción hacia la capa web.
 *
 * Representa el estado final del objeto dentro de la aplicación.
 */
public record ConstructionTypeResponseDto(
        Long id,
        String name,
        Integer estimatedDays,
        Boolean active
) {}
