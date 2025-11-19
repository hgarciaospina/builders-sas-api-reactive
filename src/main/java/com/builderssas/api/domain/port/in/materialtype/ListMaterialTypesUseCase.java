package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Flux;

/**
 * Puerto de entrada para listar TODOS los tipos de material.
 *
 * Incluye activos e inactivos.
 */
public interface ListMaterialTypesUseCase {

    Flux<MaterialTypeRecord> listAll();
}
