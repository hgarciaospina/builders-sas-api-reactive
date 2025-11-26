package com.builderssas.api.domain.port.in.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todos los movimientos relacionados con un material.
 */
public interface GetInventoryMovementsByMaterialUseCase {

    Flux<InventoryMovementRecord> findByMaterialId(Long materialId);
}
