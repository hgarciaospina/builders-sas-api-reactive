package com.builderssas.api.domain.port.in.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para la actualización de ConstructionTypeMaterial.
 */
public interface UpdateConstructionTypeMaterialUseCase {

    Mono<ConstructionTypeMaterialRecord> update(Long id, ConstructionTypeMaterialRecord record);
}
