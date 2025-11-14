package com.builderssas.api.domain.port.in.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import reactor.core.publisher.Mono;

public interface UpdateConstructionOrderUseCase {

    Mono<ConstructionOrder> update(Long id, ConstructionOrder command);
}
