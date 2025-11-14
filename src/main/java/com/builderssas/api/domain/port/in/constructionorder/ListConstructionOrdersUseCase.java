package com.builderssas.api.domain.port.in.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import reactor.core.publisher.Flux;

public interface ListConstructionOrdersUseCase {

    Flux<ConstructionOrder> listAll();
}
