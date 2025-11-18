package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.port.in.constructiontype.DeleteConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para realizar soft delete
 * sobre un tipo de construcción.
 */
@Service
@RequiredArgsConstructor
public class DeleteConstructionTypeService implements DeleteConstructionTypeUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Mono<Void> delete(Long id) {
        return repository.softDelete(id);
    }
}
