package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequest;
import com.builderssas.api.domain.port.in.constructionrequest.ListConstructionRequestsUseCase;
import com.builderssas.api.domain.port.out.ConstructionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListConstructionRequestsService implements ListConstructionRequestsUseCase {

    private final ConstructionRequestRepository repository;

    @Override
    public Flux<ConstructionRequest> listAll() {
        return repository.findAll();
    }
}
