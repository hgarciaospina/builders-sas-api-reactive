package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.in.constructionorder.ListConstructionOrdersUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListConstructionOrdersService implements ListConstructionOrdersUseCase {

    private final ConstructionOrderRepositoryPort repository;

    @Override
    public Flux<ConstructionOrderRecord> listAll() {
        return repository.findAll();
    }
}
