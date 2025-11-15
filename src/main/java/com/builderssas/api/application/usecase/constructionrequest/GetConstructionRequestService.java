package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.in.constructionrequest.GetConstructionRequestUseCase;
import com.builderssas.api.domain.port.out.ConstructionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetConstructionRequestService implements GetConstructionRequestUseCase {

    private final ConstructionRequestRepository repository;

    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return repository.findById(id);
    }
}
