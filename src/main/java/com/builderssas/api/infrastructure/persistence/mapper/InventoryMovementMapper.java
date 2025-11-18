package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.inventory.InventoryMovement;
import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper funcional que convierte entre:
 *  - InventoryMovement (dominio, record inmutable)
 *  - InventoryMovementEntity (infraestructura, entidad inmutable)
 */
@Component
public class InventoryMovementMapper {

    public InventoryMovement toDomain(InventoryMovementEntity e) {
        return new InventoryMovement(
                e.getMaterialId(),
                e.getMaterialName(),
                e.getUnitOfMeasure(),
                e.getMovementType(),
                e.getQuantity(),
                e.getPreviousStock(),
                e.getFinalStock(),
                e.getMovementDate(),
                e.getOrderId(),
                e.getReason(),
                e.getStatus(),
                e.getUserId(),
                e.getUserFullName(),
                e.getUserRole()
        );
    }

    public InventoryMovementEntity toEntity(InventoryMovement d) {
        return new InventoryMovementEntity(
                null,                   // id (autogenerado por BD)
                d.materialId(),
                d.materialName(),
                d.unitOfMeasure(),
                d.movementType(),
                d.quantity(),
                d.previousStock(),
                d.finalStock(),
                d.movementDate(),
                d.orderId(),
                d.reason(),
                d.status(),
                d.userId(),
                d.userFullName(),
                d.userRole()
        );
    }
}
