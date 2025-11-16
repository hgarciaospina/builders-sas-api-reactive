package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todos los roles asignados a un usuario.
 *
 * Este puerto permite consultar todas las relaciones activas entre un usuario
 * y los roles que tiene asociados dentro del sistema.
 */
public interface ListRolesByUserUseCase {

    /**
     * Lista todas las asignaciones de roles pertenecientes a un usuario.
     *
     * @param userId identificador del usuario.
     * @return Flux con todas las asignaciones encontradas.
     */
    Flux<UserRoleRecord> listByUser(Long userId);
}
