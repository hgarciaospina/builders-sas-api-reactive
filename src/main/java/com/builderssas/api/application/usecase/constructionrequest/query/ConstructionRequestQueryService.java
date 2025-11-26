package com.builderssas.api.application.usecase.constructionrequest.query;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Read-only query service for construction requests.
 *
 * Responsibilities:
 *  • Expose findById and listAll operations.
 *  • Keep the Orchestrator small and focused.
 */
@Service
@RequiredArgsConstructor
public class ConstructionRequestQueryService {

    private final ConstructionRequestRepositoryPort requestRepo;

    public Mono<ConstructionRequestRecord> findById(Long id) {
        return requestRepo.findById(id);
    }

    public Flux<ConstructionRequestRecord> listAll() {
        return requestRepo.findAll();
    }
}
