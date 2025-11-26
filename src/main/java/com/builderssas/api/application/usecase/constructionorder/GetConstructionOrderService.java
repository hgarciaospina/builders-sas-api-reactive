package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.in.constructionorder.GetConstructionOrderUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetConstructionOrderService implements GetConstructionOrderUseCase {

    private final ConstructionOrderRepositoryPort repository;
    @Override
    public Mono<ConstructionOrderRecord> findById(Long id) {
        return repository.findById(id);
    }

}
