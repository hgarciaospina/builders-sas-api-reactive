package com.builderssas.api.domain.port.in.userrole;

import reactor.core.publisher.Mono;

/**
 * Caso de uso para desactivar una relación usuario-rol.
 *
 * Este puerto permite realizar un "soft delete" marcando como inactiva
 * una asignación usuario-rol sin eliminarla físicamente.
 */
public interface RemoveUserRoleUseCase {

    /**
     * Desactiva una asignación usuario-rol.
     *
     * @param id identificador de la asignación.
     * @return Mono<Void> indicando la finalización de la operación.
     */
    Mono<Void> remove(Long id);
}
