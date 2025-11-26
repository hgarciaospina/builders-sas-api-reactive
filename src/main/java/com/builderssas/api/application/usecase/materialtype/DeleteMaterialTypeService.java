package com.builderssas.api.application.usecase.materialtype;

import com.builderssas.api.domain.port.in.materialtype.DeleteMaterialTypeUseCase;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para aplicar borrado lógico en tipos de material.
 */
@Service
@RequiredArgsConstructor
public class DeleteMaterialTypeService implements DeleteMaterialTypeUseCase {

    private final MaterialTypeRepositoryPort repository;

    @Override
    public Mono<Void> delete(Long id) {
        return repository.softDelete(id);
    }
}
