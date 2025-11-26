package com.builderssas.api.domain.port.in.materialtype;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para consultar un tipo de material por ID.
 *
 * Solo devuelve registros activos (active = true).
 */
public interface GetMaterialTypeUseCase {

    Mono<MaterialTypeRecord> getById(Long id);
}
