package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import com.builderssas.api.infrastructure.web.dto.inventorymovement.InventoryMovementRequestDto;
import com.builderssas.api.infrastructure.web.dto.inventorymovement.InventoryMovementResponseDto;

/**
 * Mapper Web estático para movimientos de inventario.
 *
 * Convierte:
 *  - InventoryMovementRequestDto → InventoryMovementRecord
 *  - InventoryMovementRecord → InventoryMovementResponseDto
 *
 * No usa Spring, no es un bean, no se inyecta.
 */
public final class InventoryMovementWebMapper {

    private InventoryMovementWebMapper() {}

    public static InventoryMovementRecord toDomain(InventoryMovementRequestDto dto) {
        return new InventoryMovementRecord(
                null,                       // id generado por BD
                dto.materialId(),
                dto.materialName(),
                dto.unitOfMeasure(),
                dto.movementType(),
                dto.quantity(),
                dto.previousStock(),
                dto.finalStock(),
                dto.movementDate(),
                dto.orderId(),              // null si es movimiento independiente
                dto.reason(),
                dto.status(),
                dto.userId(),
                dto.userFullName(),
                dto.userRole()
        );
    }

    public static InventoryMovementResponseDto toResponse(InventoryMovementRecord record) {
        return new InventoryMovementResponseDto(
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
