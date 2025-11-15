package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.material.MaterialTypeRecord;
import reactor.core.publisher.Mono;

public interface UpdateMaterialTypeUseCase {

    Mono<MaterialTypeRecord> update(Long id, MaterialTypeRecord command);
}
