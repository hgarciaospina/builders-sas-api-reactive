package com.builderssas.api.infrastructure.web.dto.inventory;

import java.time.LocalDateTime;

/**
 * DTO de salida para exponer el estado del inventario vivo.
 *
 * Responsabilidades:
 *  • Representar el stock actual de un material.
 *  • Ser usado únicamente en respuestas Web.
 *
 * Notas:
 *  • No contiene validaciones.
 *  • No se usa para operaciones de entrada o salida de inventario.
 */
public record InventoryDto(
        Long id,
        Long materialId,
        Double stock,
        LocalDateTime updatedAt
) {}
