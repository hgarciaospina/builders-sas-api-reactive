package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.in.constructionorder.CreateConstructionOrderUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateConstructionOrderService implements CreateConstructionOrderUseCase {

    private final ConstructionOrderRepositoryPort repository;

    @Override
    public Mono<ConstructionOrderRecord> create(ConstructionOrderRecord command) {
        return repository.save(command);
    }
}
