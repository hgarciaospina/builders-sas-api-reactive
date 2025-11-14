package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequest;
import com.builderssas.api.domain.port.in.constructionrequest.UpdateConstructionRequestUseCase;
import com.builderssas.api.domain.port.out.ConstructionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateConstructionRequestService implements UpdateConstructionRequestUseCase {

    private final ConstructionRequestRepository repository;

    @Override
    public Mono<ConstructionRequest> update(Long id, ConstructionRequest command) {
        ConstructionRequest updated = new ConstructionRequest(
            id,
            projectId, constructionTypeId, latitude, longitude, status, active
        );
        return repository.save(updated);
    }
}
