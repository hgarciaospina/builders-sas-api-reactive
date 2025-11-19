package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeCreateDto;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeUpdateDto;
import com.builderssas.api.infrastructure.web.dto.materialtype.MaterialTypeResponseDto;
import org.springframework.stereotype.Component;

/**
 * Mapper Web ↔ Dominio para MaterialType.
 */
@Component
public class MaterialTypeWebMapper {

    public MaterialTypeRecord toRecord(MaterialTypeCreateDto dto) {
        return new MaterialTypeRecord(
                null,                 // id
                dto.code(),           // code
                dto.name(),           // name
                dto.unitOfMeasure(),  // unitOfMeasure
                true                  // active por defecto
        );
    }

    public MaterialTypeRecord toRecord(Long id, MaterialTypeUpdateDto dto) {
        return new MaterialTypeRecord(
                id,
                dto.code(),
                dto.name(),
                dto.unitOfMeasure(),
                dto.active()
        );
    }

    public MaterialTypeResponseDto toResponse(MaterialTypeRecord record) {
        return new MaterialTypeResponseDto(
                record.id(),
                record.code(),
                record.name(),
                record.unitOfMeasure(),
                record.active()
        );
    }
}
