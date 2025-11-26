package com.builderssas.api.domain.port.in.materialtype;

import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para aplicar borrado lógico (soft delete)
 * a un tipo de material.
 */
public interface DeleteMaterialTypeUseCase {

    Mono<Void> delete(Long id);
}
