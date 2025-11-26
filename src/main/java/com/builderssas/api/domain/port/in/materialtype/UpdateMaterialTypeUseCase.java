package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para actualizar un tipo de material.
 */
public interface UpdateMaterialTypeUseCase {

    Mono<MaterialTypeRecord> update(Long id, MaterialTypeRecord command);
}
