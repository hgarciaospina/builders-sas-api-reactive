package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.infrastructure.persistence.entity.InventoryMovementEntity;

/**
 * Mapper funcional encargado de convertir entre:
 * - InventoryMovementRecord (dominio)
 * - InventoryMovementEntity (infraestructura)
 *
 * Este componente no contiene lógica de negocio. Su responsabilidad es
 * transformar estructuras de datos entre capas sin introducir mutación.
 *
 * Reglas:
 * - Sin programación imperativa.
 * - Sin if/else, sin ternarios y sin Optional.map.orElse.
 * - Conversiones directas campo a campo.
 * - El mapper no depende de servicios ni repositorios.
 */
public final class InventoryMovementMapper {

    private InventoryMovementMapper() {
        // Constructor privado para mantener el mapper como utilidad pura.
    }

    /**
     * Convierte una entidad persistente a un record del dominio.
     *
     * @param entity entidad persistente InventoryMovementEntity
     * @return record InventoryMovementRecord
     */
    public static InventoryMovementRecord toDomain(InventoryMovementEntity entity) {
        return new InventoryMovementRecord(
                entity.getId(),
                entity.getMaterialId(),
                entity.getMaterialName(),
                entity.getUnitOfMeasure(),
                entity.getMovementType(),
                entity.getQuantity(),
                entity.getPreviousStock(),
                entity.getFinalStock(),
                entity.getMovementDate(),
                entity.getOrderId(),
                entity.getReason(),
                entity.getStatus(),
                entity.getUserId(),
                entity.getUserFullName(),
                entity.getUserRole()
        );
    }

    /**
     * Convierte un record del dominio a una entidad persistente.
     *
     * @param record InventoryMovementRecord del dominio
     * @return InventoryMovementEntity para insertar o consultar con R2DBC
     */
    public static InventoryMovementEntity toEntity(InventoryMovementRecord record) {
        return new InventoryMovementEntity(
                record.id(),
                record.materialId(),
                record.materialName(),
                record.unitOfMeasure(),
                record.movementType(),
                record.quantity(),
                record.previousStock(),
                record.finalStock(),
                record.movementDate(),
                record.orderId(),
                record.reason(),
                record.status(),
                record.userId(),
                record.userFullName(),
                record.userRole()
        );
    }
}
