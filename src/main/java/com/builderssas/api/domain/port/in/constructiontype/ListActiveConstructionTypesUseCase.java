package com.builderssas.api.domain.port.in.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import reactor.core.publisher.Flux;

/**
 * Puerto de entrada responsable de listar únicamente los tipos de construcción activos.
 *
 * Caso de uso atómico y consistente con la arquitectura hexagonal del proyecto.
 */
public interface ListActiveConstructionTypesUseCase {

    /**
     * Lista exclusivamente los tipos de construcción con active = true.
     *
     * @return flujo reactivo con los registros activos
     */
    Flux<ConstructionTypeRecord> listActive();
}
