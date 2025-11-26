package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.infrastructure.web.dto.constructiontype.ConstructionTypeCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructiontype.ConstructionTypeUpdateDto;
import com.builderssas.api.infrastructure.web.dto.constructiontype.ConstructionTypeResponseDto;

/**
 * Mapper Web estático para convertir entre:
 *
 *   • ConstructionTypeCreateDto → ConstructionTypeRecord
 *   • ConstructionTypeUpdateDto → ConstructionTypeRecord
 *   • ConstructionTypeRecord → ConstructionTypeResponseDto
 *
 * No contiene lógica de negocio.
 * No se inyecta en controladores.
 * No depende de Spring.
 */
public final class ConstructionTypeWebMapper {

    private ConstructionTypeWebMapper() { }

    /**
     * Convierte un DTO de creación en un record del dominio.
     */
    public static ConstructionTypeRecord toRecord(ConstructionTypeCreateDto dto) {
        return new ConstructionTypeRecord(
                null,
                dto.name(),
                dto.estimatedDays(),
                true
        );
    }

    /**
     * Convierte un DTO de actualización + id en un record del dominio.
     */
    public static ConstructionTypeRecord toRecord(Long id, ConstructionTypeUpdateDto dto) {
        return new ConstructionTypeRecord(
                id,
                dto.name(),
                dto.estimatedDays(),
                true
        );
    }

    /**
     * Convierte un record del dominio en un DTO de respuesta.
     */
    public static ConstructionTypeResponseDto toResponse(ConstructionTypeRecord r) {
        return new ConstructionTypeResponseDto(
                r.id(),
                r.name(),
                r.estimatedDays(),
                r.active()
        );
    }
}
