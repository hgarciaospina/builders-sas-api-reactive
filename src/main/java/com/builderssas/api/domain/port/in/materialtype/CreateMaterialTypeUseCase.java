package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para crear un tipo de material.
 */
public interface CreateMaterialTypeUseCase {

    Mono<MaterialTypeRecord> create(MaterialTypeRecord command);
}
