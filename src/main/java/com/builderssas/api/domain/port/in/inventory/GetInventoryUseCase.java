package com.builderssas.api.domain.port.in.inventory;

import com.builderssas.api.domain.model.inventory.InventoryRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para consultar el stock vivo de un material.
 */
public interface GetInventoryUseCase {
    Mono<InventoryRecord> findByMaterialId(Long materialId);
}
