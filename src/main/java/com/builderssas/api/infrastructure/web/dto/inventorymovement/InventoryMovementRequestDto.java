package com.builderssas.api.infrastructure.web.dto.inventorymovement;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;

import java.time.LocalDateTime;

/**
 * DTO de entrada para registrar un movimiento de inventario.
 */
public record InventoryMovementRequestDto(
        Long materialId,
        String materialName,
        String unitOfMeasure,
        InventoryMovementType movementType,
        Double quantity,
        Double previousStock,
        Double finalStock,
        LocalDateTime movementDate,
        Long orderId,
        InventoryReason reason,
        InventoryStatus status,
        Long userId,
        String userFullName,
        String userRole
) { }
