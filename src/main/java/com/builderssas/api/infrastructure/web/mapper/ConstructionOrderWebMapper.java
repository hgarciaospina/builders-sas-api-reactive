package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.web.dto.constructionorder.ConstructionOrderResponseDto;
import org.springframework.stereotype.Component;

/**
 * Mapper encargado de convertir datos del dominio (ConstructionOrderRecord)
 * hacia el DTO expuesto por la capa web (ConstructionOrderResponseDto).
 *
 * Este mapper no realiza transformaciones adicionales ni lógica de negocio.
 * Su único propósito es adaptar el modelo inmutable del dominio a una
 * representación adecuada para ser enviada en las respuestas HTTP.
 */
@Component
public class ConstructionOrderWebMapper {

    public ConstructionOrderResponseDto toResponse(ConstructionOrderRecord r) {
        return new ConstructionOrderResponseDto(
                r.id(),
                r.constructionRequestId(),
                r.projectId(),
                r.constructionTypeId(),
                r.requestedByUserId(),
                r.latitude(),
                r.longitude(),
                r.requestedDate(),
                r.scheduledStartDate(),
                r.scheduledEndDate(),
                r.orderStatus(),
                r.createdAt(),
                r.updatedAt(),
                r.observations(),
                r.active()
        );
    }
}
