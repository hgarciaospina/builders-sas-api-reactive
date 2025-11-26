package com.builderssas.api.domain.model.inventory;

import java.time.LocalDateTime;

/**
 * Record de dominio que representa el stock VIVO de un material.
 *
 * Este modelo:
 *  • Pertenece a la capa de Dominio dentro de la Arquitectura Hexagonal.
 *  • No contiene lógica de negocio, solo datos inmutables.
 *  • No repite información de catálogo (nombre, unidad, código).
 *    Esos datos provienen de MaterialTypeRecord.
 *
 * Campos:
 *  • id          - Identificador único del registro de inventario.
 *  • materialId  - Identificador del material (FK a MaterialType).
 *  • stock       - Cantidad actual disponible en inventario.
 *  • updatedAt   - Fecha y hora de la última actualización del stock.
 */
public record InventoryRecord(
        Long id,
        Long materialId,
        Double stock,
        LocalDateTime updatedAt
) {}
