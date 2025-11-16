package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.in.constructionrequest.ListConstructionRequestsUseCase;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListConstructionRequestsService implements ListConstructionRequestsUseCase {

    private final ConstructionRequestRepositoryPort repository;

    @Override
    public Flux<ConstructionRequestRecord> listAll() {
        return repository.findAll();
    }
}
