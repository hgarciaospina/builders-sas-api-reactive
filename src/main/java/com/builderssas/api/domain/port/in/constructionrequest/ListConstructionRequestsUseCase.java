package com.builderssas.api.domain.port.in.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import reactor.core.publisher.Flux;

public interface ListConstructionRequestsUseCase {

    Flux<ConstructionRequestRecord> listAll();
}
