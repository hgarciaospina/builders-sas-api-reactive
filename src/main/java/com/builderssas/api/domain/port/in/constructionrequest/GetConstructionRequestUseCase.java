package com.builderssas.api.domain.port.in.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequest;
import reactor.core.publisher.Mono;

public interface GetConstructionRequestUseCase {

    Mono<ConstructionRequest> getById(Long id);
}
