package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeCreateDto;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeUpdateDto;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeResponseDto;

/**
 * Mapper Web estático para MaterialType.
 *
 * Convierte:
 *  - DTOs de entrada → Records de dominio
 *  - Records de dominio → DTOs de respuesta
 *
 * No tiene lógica de negocio ni depende de Spring.
 */
public final class MaterialTypeWebMapper {

    private MaterialTypeWebMapper() {}

    public static MaterialTypeRecord toRecord(MaterialTypeCreateDto dto) {
        return new MaterialTypeRecord(
                null,
                dto.code(),
                dto.name(),
                dto.unitOfMeasure(),
                true
        );
    }

    public static MaterialTypeRecord toRecord(Long id, MaterialTypeUpdateDto dto) {
        return new MaterialTypeRecord(
                id,
                dto.code(),
                dto.name(),
                dto.unitOfMeasure(),
                dto.active()
        );
    }

    public static MaterialTypeResponseDto toResponse(MaterialTypeRecord record) {
        return new MaterialTypeResponseDto(
                record.id(),
                record.code(),
                record.name(),
                record.unitOfMeasure(),
                record.active()
        );
    }
}
