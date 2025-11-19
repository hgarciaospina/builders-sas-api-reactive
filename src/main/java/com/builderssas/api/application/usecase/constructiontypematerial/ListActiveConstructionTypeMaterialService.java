package com.builderssas.api.application.usecase.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.in.constructiontypematerial.ListActiveConstructionTypeMaterialUseCase;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso encargado de listar únicamente los registros activos
 * de ConstructionTypeMaterial.
 */
@Service
@RequiredArgsConstructor
public final class ListActiveConstructionTypeMaterialService implements ListActiveConstructionTypeMaterialUseCase {

    private final ConstructionTypeMaterialRepositoryPort repositoryPort;

    @Override
    public Flux<ConstructionTypeMaterialRecord> listActive() {
        return repositoryPort.findAllActive();
    }
}
