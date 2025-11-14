package com.builderssas.api.domain.port.in.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionType;
import reactor.core.publisher.Mono;

public interface UpdateConstructionTypeUseCase {

    Mono<ConstructionType> update(Long id, ConstructionType command);
}
