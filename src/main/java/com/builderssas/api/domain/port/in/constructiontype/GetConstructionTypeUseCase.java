package com.builderssas.api.domain.port.in.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import reactor.core.publisher.Mono;

public interface GetConstructionTypeUseCase {

    Mono<ConstructionTypeRecord> getById(Long id);
}
