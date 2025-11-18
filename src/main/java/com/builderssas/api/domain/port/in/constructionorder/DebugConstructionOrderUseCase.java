package com.builderssas.api.domain.port.in.constructionorder;

import reactor.core.publisher.Flux;

public interface DebugConstructionOrderUseCase {
    Flux<Object> debugRaw();
}
