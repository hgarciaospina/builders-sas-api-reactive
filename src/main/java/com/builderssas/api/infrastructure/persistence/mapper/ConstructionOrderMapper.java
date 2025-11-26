package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionOrderEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversión entre:
 *  • ConstructionOrderRecord  →  ConstructionOrderEntity
 *  • ConstructionOrderEntity  →  ConstructionOrderRecord
 *
 * 100% inmutable, sin lógica de negocio, sin defaults.
 * ÚNICAMENTE traslada valores tal cual existen.
 */
@Component
public class ConstructionOrderMapper {

    /**
     * Convierte una Entity (persistencia) → Record (dominio)
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
                e.getRequestedDate(),
                e.getScheduledStartDate(),
                e.getScheduledEndDate(),
                e.getOrderStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getObservations(),
                e.getActive()
        );
    }

    /**
     * Convierte un Record (dominio) → Entity (persistencia)
     *
     * NOTA IMPORTANTE:
     *  • Se respeta EXACTAMENTE lo que viene en el record.
     *  • Si el record trae createdAt o updatedAt nulo → se guarda nulo.
     *  • La lógica de timestamps la define el UseCase, no el mapper.
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
