package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.CreateConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para crear un tipo de construcción.
 *
 * No aplica lógica de negocio. Únicamente delega al repositorio
 * utilizando programación reactiva e inmutable.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionTypeService implements CreateConstructionTypeUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Mono<ConstructionTypeRecord> create(ConstructionTypeRecord command) {
        return repository.save(command);
    }
}
