package com.builderssas.api.domain.port.in.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionType;
import reactor.core.publisher.Flux;

public interface ListConstructionTypesUseCase {

    Flux<ConstructionType> listAll();
}
