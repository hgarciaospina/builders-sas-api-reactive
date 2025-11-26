package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada del dominio que define la operación para registrar
 * un movimiento de inventario. Este caso de uso encapsula las reglas
 * de negocio asociadas al cambio de stock.
 *
 * El caso de uso recibe los datos del movimiento, calcula el stock previo
 * y el stock final, y delega la persistencia en el puerto de salida.
 */
public interface RegisterInventoryMovementUseCase {

    /**
     * Registra un movimiento de inventario aplicando las reglas de negocio.
     *
     * @param movementRequest movimiento recibido desde la capa superior
     * @return movimiento persistido con cálculos de stock incluidos
     */
    Mono<InventoryMovementRecord> registerMovement(InventoryMovementRecord movementRequest);
}
