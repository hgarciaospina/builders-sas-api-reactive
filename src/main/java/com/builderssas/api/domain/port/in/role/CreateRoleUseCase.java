package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para crear un nuevo rol.
 *
 * Contrato que define la operación que la capa de aplicación debe
 * implementar para registrar un rol en el sistema.
 */
public interface CreateRoleUseCase {

    /**
     * Crea un rol con los datos proporcionados.
     *
     * @param role datos del rol a crear
     * @return rol creado
     */
    Mono<RoleRecord> create(RoleRecord role);
}
