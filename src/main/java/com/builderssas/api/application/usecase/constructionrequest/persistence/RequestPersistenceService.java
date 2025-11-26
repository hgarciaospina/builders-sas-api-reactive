package com.builderssas.api.application.usecase.constructionrequest.persistence;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Handles persistence of construction request outcomes (approved or rejected).
 *
 * Responsibilities:
 *  • Persist the final ConstructionRequestRecord (approved or rejected).
 *  • Keep persistence logic outside the orchestrator.
 *
 * Characteristics:
 *  • Pure application service.
 *  • No business logic.
 *  • Fully reactive (Mono).
 *  • Clean, immutable operation.
 */
@Service
@RequiredArgsConstructor
public class RequestPersistenceService {

    private final ConstructionRequestRepositoryPort requestRepo;

    /**
     * Persists a final construction request state.
     */
    public Mono<ConstructionRequestRecord> save(ConstructionRequestRecord request) {
        return requestRepo.save(request);
    }
}
