package com.builderssas.api.infrastructure.web.dto.materialtype;

/**
 * DTO de salida utilizado para enviar la representación
 * del tipo de material en las respuestas HTTP.
 */
public record MaterialTypeResponseDto(
        Long id,
        String code,
        String name,
        String unitOfMeasure,
        Boolean active
) {}
