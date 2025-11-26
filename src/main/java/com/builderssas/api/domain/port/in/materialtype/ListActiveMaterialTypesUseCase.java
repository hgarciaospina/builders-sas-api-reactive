package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Flux;

/**
 * Puerto de entrada responsable de listar únicamente
 * los tipos de material activos (active = true).
 */
public interface ListActiveMaterialTypesUseCase {

    /**
     * Lista solo los tipos de material activos.
     */
    Flux<MaterialTypeRecord> listActive();
}
