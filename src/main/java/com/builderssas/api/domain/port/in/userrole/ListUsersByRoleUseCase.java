package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todos los usuarios asociados a un rol específico.
 *
 * Este puerto permite consultar qué usuarios tienen asignado un rol determinado.
 */
public interface ListUsersByRoleUseCase {

    /**
     * Lista todas las asignaciones donde el rol indicado está asociado a usuarios.
     *
     * @param roleId identificador del rol.
     * @return Flux con todas las asignaciones encontradas.
     */
    Flux<UserRoleRecord> listByRole(Long roleId);
}
