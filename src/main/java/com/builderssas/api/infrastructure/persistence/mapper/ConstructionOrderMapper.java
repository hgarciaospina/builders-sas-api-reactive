package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionOrderEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Mapper responsable de convertir entre:
 * - ConstructionOrderEntity  → persistencia (R2DBC)
 * - ConstructionOrderRecord  → dominio (modelo inmutable)
 *
 * Mantiene una correspondencia 1:1 exacta con los atributos de la entity
 * y el record, sin aplicar reglas de negocio y utilizando únicamente
 * transformaciones funcionales sin lógica imperativa.
 */
@Component
public class ConstructionOrderMapper {

    /**
     * Transforma una entity de persistencia en un record de dominio.
     *
     * @param e instancia de ConstructionOrderEntity
     * @return instancia equivalente de ConstructionOrderRecord o null si la entity es nula
     */
    public ConstructionOrderRecord toRecord(ConstructionOrderEntity e) {
        return Optional.ofNullable(e)
                .map(entity -> new ConstructionOrderRecord(
                        entity.getId(),
                        entity.getConstructionRequestId(), // requestId
                        entity.getProjectId(),             // projectId
                        entity.getConstructionTypeId(),    // constructionTypeId
                        entity.getRequestedByUserId(),     // createdByUserId
                        entity.getLatitude(),              // latitude
                        entity.getLongitude(),             // longitude
                        entity.getRequestedDate(),         // requestedDate
                        entity.getScheduledStartDate(),    // scheduledStartDate
                        entity.getScheduledEndDate(),      // scheduledEndDate
                        entity.getOrderStatus(),           // orderStatus
                        entity.getCreatedAt(),             // createdAt
                        entity.getUpdatedAt(),             // updatedAt
                        entity.getObservations(),          // observations
                        entity.getActive()                 // active
                ))
                .orElse(null);
    }

    /**
     * Transforma un record del dominio en su entity equivalente para persistencia.
     *
     * @param r instancia de ConstructionOrderRecord
     * @return entity resultante o null si el record es nulo
     */
    public ConstructionOrderEntity toEntity(ConstructionOrderRecord r) {
        return Optional.ofNullable(r)
                .map(record -> new ConstructionOrderEntity(
                        record.id(),
                        record.constructionRequestId(),
                        record.projectId(),
                        record.constructionTypeId(),
                        record.requestedByUserId(),
                        record.latitude(),
                        record.longitude(),
                        record.requestedDate(),
                        record.scheduledStartDate(),
                        record.scheduledEndDate(),
                        record.orderStatus(),
                        record.createdAt(),
                        record.updatedAt(),
                        record.observations(),
                        record.active()
                ))
                .orElse(null);
    }
}
