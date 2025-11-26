package com.builderssas.api.domain.port.in.inventory;

import reactor.core.publisher.Mono;

/**
 * Incrementa el stock vivo de un material.
 */
public interface IncreaseInventoryUseCase {
    Mono<Void> increase(Long materialId, Double quantity);
}
