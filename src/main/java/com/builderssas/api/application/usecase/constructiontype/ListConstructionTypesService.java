package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.ListConstructionTypesUseCase;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListConstructionTypesService implements ListConstructionTypesUseCase {

    private final ConstructionTypeRepository repository;

    @Override
    public Flux<ConstructionTypeRecord> listAll() {
        return repository.findAll();
    }
}
