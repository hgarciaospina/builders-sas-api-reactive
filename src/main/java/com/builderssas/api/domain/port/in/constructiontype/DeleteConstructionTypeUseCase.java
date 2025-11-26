package com.builderssas.api.domain.port.in.constructiontype;

import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para aplicar borrado lógico (soft delete)
 * sobre un tipo de construcción dentro de la Arquitectura Hexagonal.
 *
 * Este contrato define una operación reactiva que permite marcar
 * un registro como inactivo mediante la propiedad "active = false".
 *
 * No elimina datos físicamente; garantiza trazabilidad y
 * consistencia histórica de solicitudes, órdenes e inventario.
 */
public interface DeleteConstructionTypeUseCase {

    /**
     * Marca un tipo de construcción como inactivo.
     *
     * @param id identificador del tipo de construcción a desactivar
     * @return Mono vacío que indica finalización del proceso
     */
    Mono<Void> delete(Long id);
}
