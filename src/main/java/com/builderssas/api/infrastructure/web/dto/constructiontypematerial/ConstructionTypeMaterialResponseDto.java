package com.builderssas.api.infrastructure.web.dto.constructiontypematerial;

/**
 * DTO enviado al cliente.
 * No expone lógica ni mutaciones.
 */
public record ConstructionTypeMaterialResponseDto(
        Long id,
        Long constructionTypeId,
        Long materialTypeId,
        Double quantity,
        Boolean active
) {}
