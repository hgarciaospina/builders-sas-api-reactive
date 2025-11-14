package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.material.MaterialType;
import reactor.core.publisher.Mono;

public interface UpdateMaterialTypeUseCase {

    Mono<MaterialType> update(Long id, MaterialType command);
}
