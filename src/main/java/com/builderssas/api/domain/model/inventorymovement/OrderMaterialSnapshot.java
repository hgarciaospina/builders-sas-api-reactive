package com.builderssas.api.domain.model.inventorymovement;

import java.time.LocalDateTime;

/**
 * Snapshot inmutable del material consumido durante la creación
 * de una orden de construcción.
 *
 * Su propósito es conservar un "congelado histórico" del estado
 * del inventario en el instante exacto en el que la orden fue creada.
 *
 * Razones principales para este snapshot:
 * - Evita inconsistencias históricas si cambian los materiales.
 * - Permite auditoría exacta de consumos.
 * - Permite reconstruir reportes y dashboards sin depender
 *   del estado actual del inventario.
 * - Queda totalmente desacoplado de las entidades vivas
 *   (MaterialType, ConstructionType, etc).
 */
public record OrderMaterialSnapshot(

        /**
         * ID del material al momento de la creación de la orden.
         * (Congelado; si el ID cambia en catálogo vivo, el snapshot se mantiene).
         */
        Long materialId,

        /**
         * Nombre del material en el momento del consumo.
         */
        String materialName,

        /**
         * Unidad de medida del material (ejemplo: kg, m3, unidades).
         */
        String unitOfMeasure,

        /**
         * Stock registrado ANTES de aplicar el consumo.
         */
        Double stockBefore,

        /**
         * Cantidad requerida por la orden.
         * Siempre POSITIVA.
         */
        Double requiredQuantity,

        /**
         * Stock resultante DESPUÉS del consumo aplicado.
         */
        Double stockAfter,

        /**
         * Fecha y hora exacta en la que este snapshot fue generado.
         * Normalmente coincide con la fecha de creación de la orden.
         */
        LocalDateTime snapshotDate
) {}
