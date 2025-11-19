package com.builderssas.api.application.usecase.constructiontypematerial;

import com.builderssas.api.domain.port.in.constructiontypematerial.DeleteConstructionTypeMaterialUseCase;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsable de realizar soft delete sobre un registro ConstructionTypeMaterial.
 */
@Service
@RequiredArgsConstructor
public final class DeleteConstructionTypeMaterialService implements DeleteConstructionTypeMaterialUseCase {

    private final ConstructionTypeMaterialRepositoryPort repositoryPort;

    @Override
    public Mono<Void> delete(Long id) {
        return repositoryPort.softDelete(id);
    }
}
