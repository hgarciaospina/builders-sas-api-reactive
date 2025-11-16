package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Flux;

public interface ListMaterialTypesUseCase {

    Flux<MaterialTypeRecord> listAll();
}
