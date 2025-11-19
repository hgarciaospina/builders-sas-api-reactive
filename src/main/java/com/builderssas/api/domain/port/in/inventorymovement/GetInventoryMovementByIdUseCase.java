package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventory.InventoryMovementRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener un movimiento de inventario a partir
 * de su identificador único.
 */
public interface GetInventoryMovementByIdUseCase {

    Mono<InventoryMovementRecord> findById(Long id);
}
