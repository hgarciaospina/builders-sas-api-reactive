package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Mono;

public interface GetMaterialTypeUseCase {

    Mono<MaterialTypeRecord> getById(Long id);
}
