package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.material.MaterialTypeRecord;
import reactor.core.publisher.Flux;

public interface ListMaterialTypesUseCase {

    Flux<MaterialTypeRecord> listAll();
}
