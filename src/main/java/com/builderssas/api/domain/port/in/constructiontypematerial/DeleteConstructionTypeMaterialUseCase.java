package com.builderssas.api.domain.port.in.constructiontypematerial;

import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para realizar un soft delete de ConstructionTypeMaterial.
 */
public interface DeleteConstructionTypeMaterialUseCase {

    Mono<Void> delete(Long id);
}
