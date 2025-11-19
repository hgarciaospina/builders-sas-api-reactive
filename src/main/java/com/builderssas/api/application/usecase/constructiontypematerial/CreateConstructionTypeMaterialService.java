package com.builderssas.api.application.usecase.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.in.constructiontypematerial.CreateConstructionTypeMaterialUseCase;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsable de crear un registro ConstructionTypeMaterial.
 *
 * Implementa el puerto de entrada correspondiente y delega la operación
 * al puerto de salida encargado de persistencia.
 *
 * 100% reactivo y alineado con la arquitectura hexagonal del sistema.
 */
@Service
@RequiredArgsConstructor
public final class CreateConstructionTypeMaterialService implements CreateConstructionTypeMaterialUseCase {

    private final ConstructionTypeMaterialRepositoryPort repositoryPort;

    @Override
    public Mono<ConstructionTypeMaterialRecord> create(ConstructionTypeMaterialRecord record) {
        return repositoryPort.save(record);
    }
}
