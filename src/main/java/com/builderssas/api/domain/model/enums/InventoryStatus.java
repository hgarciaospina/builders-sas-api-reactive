package com.builderssas.api.domain.model.enums;

/**
 * Enum que representa el ESTADO de un movimiento de inventario
 * dentro de su ciclo de vida.
 *
 * Cada estado permite auditar, validar y controlar cómo avanza
 * el movimiento desde su creación hasta su aplicación o anulación.
 *
 * Este estado es independiente de:
 * - la dirección del movimiento (IN / OUT),
 * - y la razón del movimiento (InventoryReason).
 */
public enum InventoryStatus {

    /**
     * Movimiento registrado pero aún no validado ni aplicado.
     */
    PENDING("Movimiento registrado y pendiente de validación."),

    /**
     * Movimiento aplicado correctamente al inventario.
     */
    COMPLETED("Movimiento completado y aplicado al inventario."),

    /**
     * Movimiento revertido mediante un registro compensatorio.
     */
    REVERSED("Movimiento revertido mediante un movimiento inverso."),

    /**
     * Movimiento cancelado antes de afectar el inventario.
     */
    CANCELLED("Movimiento cancelado antes de modificar el inventario."),

    /**
     * Movimiento fallido por inconsistencias o errores del sistema.
     */
    FAILED("Movimiento fallido debido a errores o datos inconsistentes.");

    private final String description;

    InventoryStatus(String description) {
        this.description = description;
    }

    /**
     * Retorna la descripción legible del estado,
     * útil para reportes, pantallas, auditoría y logs.
     */
    public String description() {
        return description;
    }
}
