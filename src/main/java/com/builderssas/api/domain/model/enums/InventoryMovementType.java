package com.builderssas.api.domain.model.enums;

/**
 * Enum que representa la dirección de un movimiento de inventario.
 *
 * IN  → El inventario aumenta.
 * OUT → El inventario disminuye.
 *
 * Este enum NO define cantidades ni cálculos; su única responsabilidad
 * es indicar la dirección del movimiento. La lógica de sumar o restar
 * se define en el caso de uso correspondiente, manteniendo el dominio
 * limpio y expresivo.
 */
public enum InventoryMovementType {

    /**
     * Movimiento de ENTRADA.
     * El inventario incrementa.
     * Ejemplos:
     * - Compras de materiales.
     * - Devoluciones al almacén.
     * - Reposición manual.
     * - Conteos físicos que corrigen el stock hacia arriba.
     */
    IN,

    /**
     * Movimiento de SALIDA.
     * El inventario disminuye.
     * Ejemplos:
     * - Consumo por creación de órdenes de construcción.
     * - Pérdidas o material dañado.
     * - Ajustes que reducen el stock.
     * - Transferencias hacia otro almacén.
     */
    OUT
}
