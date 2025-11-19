package com.builderssas.api.domain.port.in.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para obtener un ConstructionTypeMaterial por ID.
 */
public interface GetConstructionTypeMaterialUseCase {

    Mono<ConstructionTypeMaterialRecord> findById(Long id);
}
