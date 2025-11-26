package com.builderssas.api.application.usecase.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.in.constructiontypematerial.UpdateConstructionTypeMaterialUseCase;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso responsable de actualizar un registro ConstructionTypeMaterial.
 *
 * La lógica de negocio se mantiene fuera de la capa de infraestructura,
 * delegando en el puerto OUT la operación de persistencia.
 */
@Service
@RequiredArgsConstructor
public final class UpdateConstructionTypeMaterialService implements UpdateConstructionTypeMaterialUseCase {

    private final ConstructionTypeMaterialRepositoryPort repositoryPort;

    @Override
    public Mono<ConstructionTypeMaterialRecord> update(Long id, ConstructionTypeMaterialRecord record) {
        // La regla de negocio es que el ID del path prevalece sobre el del record
        ConstructionTypeMaterialRecord updatedRecord =
                new ConstructionTypeMaterialRecord(
                        id,
                        record.constructionTypeId(),
                        record.materialTypeId(),
                        record.quantityRequired(),
                        record.active()
                );

        return repositoryPort.save(updatedRecord);
    }
}
