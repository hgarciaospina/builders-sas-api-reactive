package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para consultar todos los movimientos asociados a una orden.
 */
public interface GetInventoryMovementsByOrderUseCase {

    Flux<InventoryMovementRecord> findByOrderId(Long orderId);
}
