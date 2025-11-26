package com.builderssas.api.domain.model.inventorymovement;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryReason;
import com.builderssas.api.domain.model.enums.InventoryStatus;

import java.time.LocalDateTime;

/**
 * Record de dominio que representa un movimiento de inventario.
 *
 * Este modelo pertenece al dominio y encapsula un evento inmutable.
 * Un movimiento de inventario siempre debe quedar registrado como
 * un hecho histórico inalterable. No existen reglas de borrado,
 * actualizaciones ni mutaciones posteriores.
 *
 * Cada campo corresponde a un valor de negocio que se congela en
 * el instante del movimiento: nombres, unidades, rol del usuario,
 * cantidades y stocks.
 *
 * Este record es consumido por los casos de uso y por los puertos
 * de salida, sin depender de detalles de infraestructura.
 *
 * Arquitectura: Hexagonal.
 * Principios: Inmutabilidad, ausencia de lógica imperativa y
 * separación clara entre dominio e infraestructura.
 */
public record InventoryMovementRecord(

        Long id,

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

) {}
