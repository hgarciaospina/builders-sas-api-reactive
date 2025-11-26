package com.builderssas.api.domain.port.in.inventory;

import reactor.core.publisher.Mono;

public interface ValidateEnoughStockUseCase {
    Mono<Boolean> hasEnough(Long materialId, Double required);
}
