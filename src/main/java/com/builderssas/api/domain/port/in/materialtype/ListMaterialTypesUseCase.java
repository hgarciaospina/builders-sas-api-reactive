package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.material.MaterialType;
import reactor.core.publisher.Flux;

public interface ListMaterialTypesUseCase {

    Flux<MaterialType> listAll();
}
