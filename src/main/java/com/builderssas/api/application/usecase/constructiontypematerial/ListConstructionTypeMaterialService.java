package com.builderssas.api.application.usecase.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.in.constructiontypematerial.ListConstructionTypeMaterialUseCase;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso encargado de listar todos los registros ConstructionTypeMaterial.
 */
@Service
@RequiredArgsConstructor
public final class ListConstructionTypeMaterialService implements ListConstructionTypeMaterialUseCase {

    private final ConstructionTypeMaterialRepositoryPort repositoryPort;

    @Override
    public Flux<ConstructionTypeMaterialRecord> findAll() {
        return repositoryPort.findAll();
    }
}
