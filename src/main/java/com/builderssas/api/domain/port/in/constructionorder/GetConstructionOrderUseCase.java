package com.builderssas.api.domain.port.in.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetConstructionOrderUseCase {
    Mono<ConstructionOrderRecord> findById(Long id);
}
