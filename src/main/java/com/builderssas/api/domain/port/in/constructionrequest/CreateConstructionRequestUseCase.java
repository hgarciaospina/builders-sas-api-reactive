package com.builderssas.api.domain.port.in.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import reactor.core.publisher.Mono;

public interface CreateConstructionRequestUseCase {

    Mono<ConstructionRequestRecord> create(ConstructionRequestRecord command);
}
