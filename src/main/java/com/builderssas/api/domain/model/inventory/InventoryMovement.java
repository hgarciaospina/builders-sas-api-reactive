package com.builderssas.api.domain.model.inventory;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.enums.InventoryReason;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Modelo de dominio que representa un movimiento de inventario.
 *
 * Este registro es INMUTABLE y expresa de manera clara toda la información
 * necesaria para auditar, rastrear y comprender un ajuste al inventario.
 *
 * Un movimiento de inventario puede ser:
 * - de entrada (IN),
 * - de salida (OUT),
 * - provenir de una orden,
 * - resultar de una reposición manual,
 * - o surgir de un ajuste de conteo físico.
 *
 * La responsabilidad de este modelo es almacenar el "hecho histórico"
 * con toda su información congelada. No está atado a la entidad viva
 * MaterialType, lo que garantiza consistencia en reportes y auditoría.
 */
public record InventoryMovement(

        /**
         * Identificador del material en el catálogo vivo (MaterialType).
         */
        Long materialId,

        /**
         * Nombre del material en el momento del movimiento.
         * Se congela por si el nombre cambia en el futuro.
         */
        String materialName,

        /**
         * Unidad de medida del material al momento del movimiento.
         */
        String unitOfMeasure,

        /**
         * Dirección del movimiento (IN / OUT).
         */
        InventoryMovementType movementType,

        /**
         * Cantidad movida. SIEMPRE POSITIVA.
         * La dirección la determina movementType.
         */
        Double quantity,

        /**
         * Stock antes de aplicar el movimiento.
         */
        Double previousStock,

        /**
         * Stock después de aplicar el movimiento.
         */
        Double finalStock,

        /**
         * Fecha y hora exacta en que el movimiento fue registrado.
         */
        LocalDateTime movementDate,

        /**
         * ID de la orden que generó el movimiento (solo si aplica).
         * Puede ser null si el movimiento no proviene de una orden.
         */
        Long orderId,

        /**
         * Motivo de negocio del movimiento.
         * Ej: PURCHASE, ORDER_CONSUMPTION, RETURN, DAMAGE.
         */
        InventoryReason reason,

        /**
         * Estado del movimiento dentro de su ciclo de vida.
         * Ej: PENDING, COMPLETED, REVERSED.
         */
        InventoryStatus status,

        /**
         * ID del usuario responsable del movimiento.
         * Este campo es obligatorio para trazabilidad.
         */
        Long userId,

        /**
         * Nombre completo del usuario en el momento del movimiento.
         * Se congela para preservar consistencia histórica.
         */
        String userFullName,

        /**
         * Rol del usuario al momento del movimiento.
         * Permite auditoría más detallada.
         */
        String userRole
) {

    /**
     * Devuelve el orderId como Optional para facilitar pipelines funcionales
     * sin necesidad de validaciones nulas explícitas.
     */
    public Optional<Long> orderIdOptional() {
        return Optional.ofNullable(orderId);
    }
}
