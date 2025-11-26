package com.builderssas.api.domain.model.ordermaterialsnapshot;

import java.time.LocalDateTime;

/**
 * Modelo de dominio que representa un snapshot INMUTABLE del consumo de materiales
 * asociado a una orden de construcción específica.
 *
 * Conserva el estado histórico exacto de inventario al momento de la creación de la orden.
 */
public record OrderMaterialSnapshotRecord(

        Long id,                // Identificador único del snapshot
        Long orderId,           // ID de la orden asociada
        Long materialId,        // ID del material congelado
        String materialName,    // Nombre del material en ese instante
        String unitOfMeasure,   // Unidad de medida

        Double stockBefore,     // Stock antes del consumo
        Double requiredQuantity,// Cantidad requerida por la orden
        Double stockAfter,      // Stock después del consumo

        LocalDateTime snapshotDate, // Fecha exacta del snapshot
        LocalDateTime createdAt,    // Fecha de creación del registro
        LocalDateTime updatedAt     // Fecha de última actualización (normalmente = createdAt)
) {}
