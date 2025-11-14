package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequest;
import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.out.ConstructionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateConstructionRequestService implements CreateConstructionRequestUseCase {

    private final ConstructionRequestRepository repository;

    @Override
    public Mono<ConstructionRequest> create(ConstructionRequest command) {
        return repository.save(command);
    }
}
