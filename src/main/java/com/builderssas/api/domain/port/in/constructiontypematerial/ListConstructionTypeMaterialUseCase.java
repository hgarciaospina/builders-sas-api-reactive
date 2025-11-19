package com.builderssas.api.domain.port.in.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import reactor.core.publisher.Flux;

/**
 * Puerto de entrada para listar ConstructionTypeMaterial.
 */
public interface ListConstructionTypeMaterialUseCase {

    Flux<ConstructionTypeMaterialRecord> findAll();
}
