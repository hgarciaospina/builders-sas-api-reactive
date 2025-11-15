package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.material.MaterialTypeRecord;
import reactor.core.publisher.Mono;

public interface GetMaterialTypeUseCase {

    Mono<MaterialTypeRecord> getById(Long id);
}
