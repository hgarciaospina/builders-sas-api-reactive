package com.builderssas.api.domain.port.in.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para la creación de ConstructionTypeMaterial.
 */
public interface CreateConstructionTypeMaterialUseCase {

    Mono<ConstructionTypeMaterialRecord> create(ConstructionTypeMaterialRecord record);
}
