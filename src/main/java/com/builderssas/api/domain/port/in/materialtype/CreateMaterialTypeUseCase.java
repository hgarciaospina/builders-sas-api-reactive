package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.material.MaterialType;
import reactor.core.publisher.Mono;

public interface CreateMaterialTypeUseCase {

    Mono<MaterialType> create(MaterialType command);
}
