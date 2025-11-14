package com.builderssas.api.domain.port.in.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import reactor.core.publisher.Mono;

public interface CreateConstructionOrderUseCase {

    Mono<ConstructionOrder> create(ConstructionOrder command);
}
