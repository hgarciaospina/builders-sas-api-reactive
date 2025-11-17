package com.builderssas.api.infrastructure.web.mapper.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.web.dto.constructionorder.ConstructionOrderResponseDto;
import org.springframework.stereotype.Component;

/**
 * Mapper Web encargado de convertir:
 *
 *   • ConstructionOrderRecord → ConstructionOrderResponseDto
 *
 * Su función es estrictamente de transformación entre capas,
 * sin incluir ninguna lógica de negocio ni validaciones.
 *
 * Todas las transformaciones mantienen el principio de inmutabilidad
 * y siguen el flujo funcional del sistema.
 */
@Component
public class ConstructionOrderWebMapper {

    /**
     * Convierte un record de dominio en un DTO de respuesta.
     *
     * @param r instancia inmutable de ConstructionOrderRecord
     * @return DTO listo para ser enviado al cliente
     */
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
