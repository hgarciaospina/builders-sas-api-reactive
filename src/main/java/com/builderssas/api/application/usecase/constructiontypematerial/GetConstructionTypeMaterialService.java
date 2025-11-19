package com.builderssas.api.application.usecase.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.in.constructiontypematerial.GetConstructionTypeMaterialUseCase;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso encargado de obtener un registro ConstructionTypeMaterial
 * por su identificador único.
 */
@Service
@RequiredArgsConstructor
public final class GetConstructionTypeMaterialService implements GetConstructionTypeMaterialUseCase {

    private final ConstructionTypeMaterialRepositoryPort repositoryPort;

    @Override
    public Mono<ConstructionTypeMaterialRecord> findById(Long id) {
        return repositoryPort.findById(id);
    }
}
