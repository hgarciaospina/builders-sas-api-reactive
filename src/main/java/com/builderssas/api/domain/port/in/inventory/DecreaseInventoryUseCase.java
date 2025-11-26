package com.builderssas.api.domain.port.in.inventory;

import reactor.core.publisher.Mono;

public interface DecreaseInventoryUseCase {
    Mono<Void> decrease(Long materialId, Double quantity);
}
