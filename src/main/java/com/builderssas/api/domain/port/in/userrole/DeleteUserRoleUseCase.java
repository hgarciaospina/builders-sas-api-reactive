package com.builderssas.api.domain.port.in.userrole;

import reactor.core.publisher.Mono;

/**
 * Caso de uso para realizar un borrado lógico (soft delete)
 * de una asignación usuario-rol.
 */
public interface DeleteUserRoleUseCase {
    Mono<Void> delete(Long id);
}
