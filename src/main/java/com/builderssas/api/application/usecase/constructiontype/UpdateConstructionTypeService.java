package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.UpdateConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para actualizar tipos de construcción.
 *
 * No se permite cambiar el estado activo aquí; solo datos.
 */
@Service
@RequiredArgsConstructor
public class UpdateConstructionTypeService implements UpdateConstructionTypeUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Mono<ConstructionTypeRecord> update(Long id, ConstructionTypeRecord command) {
        return repository.findById(id)
                .flatMap(existing ->
                        repository.save(
                                new ConstructionTypeRecord(
                                        id,
                                        command.name(),
                                        command.estimatedDays(),
                                        existing.active() // no se modifica aquí
                                )
                        )
                );
    }
}
