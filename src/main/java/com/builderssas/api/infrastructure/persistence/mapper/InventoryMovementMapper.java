package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.inventory.InventoryMovement;
import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;

/**
 * Mapper encargado de convertir entre:
 * - InventoryMovement (dominio)
 * - InventoryMovementEntity (infraestructura / BD)
 *
 * Mantiene la arquitectura hexagonal limpia, sin acoplar el dominio
 * a tecnología de persistencia.
 */
public final class InventoryMovementMapper {

    private InventoryMovementMapper() {}

    public static InventoryMovementEntity toEntity(InventoryMovement domain) {
        var entity = new InventoryMovementEntity();
        entity.setId(null); // auto increment
        entity.setMaterialId(domain.materialId());
        entity.setMaterialName(domain.materialName());
        entity.setUnitOfMeasure(domain.unitOfMeasure());
        entity.setMovementType(domain.movementType());
        entity.setQuantity(domain.quantity());
        entity.setPreviousStock(domain.previousStock());
        entity.setFinalStock(domain.finalStock());
        entity.setMovementDate(domain.movementDate());
        entity.setOrderId(domain.orderId());
        entity.setReason(domain.reason());
        entity.setStatus(domain.status());
        entity.setUserId(domain.userId());
        entity.setUserFullName(domain.userFullName());
        entity.setUserRole(domain.userRole());
        return entity;
    }

    public static InventoryMovement toDomain(InventoryMovementEntity e) {
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
}
