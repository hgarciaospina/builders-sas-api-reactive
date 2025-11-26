package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.ListConstructionTypesUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para listar únicamente los tipos activos.
 */
@Service
@RequiredArgsConstructor
public class ListConstructionTypesService implements ListConstructionTypesUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Flux<ConstructionTypeRecord> listAll() {
        return repository.findAll();
    }

}
