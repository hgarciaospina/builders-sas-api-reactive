package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionOrderEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper funcional e inmutable encargado de convertir entre:
 *  - ConstructionOrderEntity (infraestructura)
 *  - ConstructionOrderRecord (dominio)
 *
 * Sin programación imperativa, sin nulls, sin setters.
 * Correspondencia 1:1 con los atributos reales.
 */
@Component
public class ConstructionOrderMapper {

    /**
     * Convierte una entity en un record de dominio.
     * Nunca retorna null.
     */
    public ConstructionOrderRecord toRecord(ConstructionOrderEntity e) {
        return new ConstructionOrderRecord(
                e.getId(),
                e.getConstructionRequestId(),
                e.getProjectId(),
                e.getConstructionTypeId(),
                e.getRequestedByUserId(),
                e.getLatitude(),
                e.getLongitude(),
                e.getRequestedDate(),       // LocalDate
                e.getScheduledStartDate(),  // LocalDate
                e.getScheduledEndDate(),    // LocalDate
                e.getOrderStatus(),         // Enum
                e.getCreatedAt(),           // LocalDateTime
                e.getUpdatedAt(),           // LocalDateTime
                e.getObservations(),
                e.getActive()
        );
    }

    /**
     * Convierte un record de dominio en una entity inmutable.
     * Nunca retorna null.
     */
    public ConstructionOrderEntity toEntity(ConstructionOrderRecord r) {
        return new ConstructionOrderEntity(
                r.id(),
                r.constructionRequestId(),
                r.projectId(),
                r.constructionTypeId(),
                r.requestedByUserId(),
                r.latitude(),
                r.longitude(),
                r.requestedDate(),          // LocalDate
                r.scheduledStartDate(),     // LocalDate
                r.scheduledEndDate(),       // LocalDate
                r.orderStatus(),
                r.createdAt(),              // LocalDateTime
                r.updatedAt(),              // LocalDateTime
                r.observations(),
                r.active()
        );
    }
}
