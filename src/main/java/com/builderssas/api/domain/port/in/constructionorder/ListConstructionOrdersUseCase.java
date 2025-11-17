package com.builderssas.api.domain.port.in.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import reactor.core.publisher.Flux;

public interface ListConstructionOrdersUseCase {
    Flux<ConstructionOrderRecord> listAll();
}
