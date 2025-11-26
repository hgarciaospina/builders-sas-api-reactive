package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.infrastructure.web.dto.constructiontypematerial.ConstructionTypeMaterialCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructiontypematerial.ConstructionTypeMaterialUpdateDto;
import com.builderssas.api.infrastructure.web.dto.constructiontypematerial.ConstructionTypeMaterialResponseDto;

/**
 * Mapper Web para transformar DTOs ↔ Records del dominio relacionados con
 * ConstructionTypeMaterial.
 *
 * Totalmente estático, sin @Component, sin inyección.
 * No contiene lógica de negocio.
 */
public final class ConstructionTypeMaterialWebMapper {

    private ConstructionTypeMaterialWebMapper() {}

    /**
     * Convierte un DTO de creación a un record del dominio.
     */
    public static ConstructionTypeMaterialRecord toDomain(ConstructionTypeMaterialCreateDto dto) {
        return new ConstructionTypeMaterialRecord(
                null,
                dto.constructionTypeId(),
                dto.materialTypeId(),
                dto.quantity(),            // ← AHORA quantityRequired = dto.quantity()
                true
        );
    }

    /**
     * Convierte un DTO de actualización a un record del dominio.
     *
     * SOLO quantityRequired puede cambiar.
     * constructionTypeId y materialTypeId se conservan en el UseCase.
     */
    public static ConstructionTypeMaterialRecord toDomain(ConstructionTypeMaterialUpdateDto dto) {
        return new ConstructionTypeMaterialRecord(
                dto.id(),
                null,                       // lo conserva el UseCase
                null,                       // lo conserva el UseCase
                dto.quantity(),
                true
        );
    }

    /**
     * Convierte un record del dominio en un DTO para la web.
     */
    public static ConstructionTypeMaterialResponseDto toResponse(ConstructionTypeMaterialRecord r) {
        return new ConstructionTypeMaterialResponseDto(
                r.id(),
                r.constructionTypeId(),
                r.materialTypeId(),
                r.quantityRequired(),       // ← CORRECTO
                r.active()
        );
    }
}
