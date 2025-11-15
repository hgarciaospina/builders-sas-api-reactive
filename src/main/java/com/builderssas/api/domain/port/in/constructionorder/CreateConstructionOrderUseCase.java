package com.builderssas.api.domain.port.in.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import reactor.core.publisher.Mono;

public interface CreateConstructionOrderUseCase {

    Mono<ConstructionOrderRecord> create(ConstructionOrderRecord command);
}
