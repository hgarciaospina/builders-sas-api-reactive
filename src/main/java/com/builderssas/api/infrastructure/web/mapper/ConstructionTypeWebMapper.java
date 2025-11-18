package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.infrastructure.web.dto.constructiontype.ConstructionTypeCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructiontype.ConstructionTypeUpdateDto;
import com.builderssas.api.infrastructure.web.dto.constructiontype.ConstructionTypeResponseDto;

import org.springframework.stereotype.Component;

/**
 * Mapper responsable de convertir entre los DTO utilizados en la capa web
 * y los records del dominio para tipos de construcción.
 */
@Component
public class ConstructionTypeWebMapper {

    public ConstructionTypeRecord toRecord(ConstructionTypeCreateDto dto) {
        return new ConstructionTypeRecord(
                null,
                dto.name(),
                dto.estimatedDays(),
                true
        );
    }

    public ConstructionTypeRecord toRecord(Long id, ConstructionTypeUpdateDto dto) {
        return new ConstructionTypeRecord(
                id,
                dto.name(),
                dto.estimatedDays(),
                true // el estado se gestiona por delete-soft
        );
    }

    public ConstructionTypeResponseDto toResponse(ConstructionTypeRecord r) {
        return new ConstructionTypeResponseDto(
                r.id(),
                r.name(),
                r.estimatedDays(),
                r.active()
        );
    }
}
