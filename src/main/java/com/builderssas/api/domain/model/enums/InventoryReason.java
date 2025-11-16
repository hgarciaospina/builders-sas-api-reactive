package com.builderssas.api.domain.model.enums;

/**
 * Enum que representa la RAZÓN o MOTIVO de un movimiento de inventario.
 *
 * A diferencia de InventoryStatus (estado) y InventoryMovementType (dirección),
 * este enum describe la causa de negocio que originó el movimiento.
 *
 * Su propósito es permitir trazabilidad empresarial, auditoría, análisis
 * de patrones de consumo y comprensión clara del motivo detrás
 * de cada ajuste de inventario.
 */
public enum InventoryReason {

    /**
     * Consumo generado automáticamente al crear una orden
     * de construcción. Representa una salida (OUT).
     */
    ORDER_CONSUMPTION("Consumo de material generado por una orden de construcción."),

    /**
     * Ingreso al inventario debido a una compra de materiales.
     */
    PURCHASE("Ingreso por compra de materiales."),

    /**
     * Reposición manual realizada por un usuario autorizado.
     * Normalmente corresponde a una entrada (IN).
     */
    MANUAL_REPLENISH("Reposición manual de inventario."),

    /**
     * Ajuste generado por conteo físico o corrección de stock.
     * Puede ser entrada o salida.
     */
    INVENTORY_ADJUSTMENT("Ajuste de inventario por conteo físico o corrección."),

    /**
     * Material que regresa al almacén después de devolución.
     */
    RETURN("Devolución de materiales al inventario."),

    /**
     * Stock inicial asignado al crear un nuevo tipo de material.
     */
    INITIAL_STOCK("Stock inicial asignado al crear el material."),

    /**
     * Material perdido, dañado o deteriorado que debe retirarse del inventario.
     */
    DAMAGE("Salida de inventario por material dañado o deteriorado."),

    /**
     * Movimiento generado por traslado de productos entre almacenes.
     */
    WAREHOUSE_TRANSFER("Movimiento por transferencia entre almacenes.");

    private final String description;

    InventoryReason(String description) {
        this.description = description;
    }

    /**
     * Devuelve una descripción clara y legible del motivo,
     * útil para reportes, auditorías, interfaces y análisis empresarial.
     */
    public String description() {
        return description;
    }
}
