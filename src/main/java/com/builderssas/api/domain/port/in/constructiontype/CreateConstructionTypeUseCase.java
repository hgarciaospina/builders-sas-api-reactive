package com.builderssas.api.domain.port.in.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionType;
import reactor.core.publisher.Mono;

public interface CreateConstructionTypeUseCase {

    Mono<ConstructionType> create(ConstructionType command);
}
