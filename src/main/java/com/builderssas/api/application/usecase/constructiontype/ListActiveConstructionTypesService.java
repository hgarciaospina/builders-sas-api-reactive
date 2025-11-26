package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.ListActiveConstructionTypesUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso encargado de listar únicamente
 * los tipos de construcción activos.
 *
 * Delegación total al puerto de salida sin lógica adicional.
 */
@Service
@RequiredArgsConstructor
public class ListActiveConstructionTypesService implements ListActiveConstructionTypesUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Flux<ConstructionTypeRecord> listActive() {
        return repository.findAllActive();
    }
}
