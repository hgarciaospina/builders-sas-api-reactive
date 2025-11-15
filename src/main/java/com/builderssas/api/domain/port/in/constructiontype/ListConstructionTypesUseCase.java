package com.builderssas.api.domain.port.in.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import reactor.core.publisher.Flux;

public interface ListConstructionTypesUseCase {

    Flux<ConstructionTypeRecord> listAll();
}
