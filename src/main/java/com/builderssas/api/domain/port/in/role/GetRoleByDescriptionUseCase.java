package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener un rol por su descripción.
 *
 * Utilizado para prevenir duplicados y mantener
 * consistencia en la capa de dominio.
 */
public interface GetRoleByDescriptionUseCase {

    /**
     * Busca un rol por su descripción.
     *
     * @param description descripción del rol
     * @return Mono con el RoleRecord si existe, o Mono.empty() si no existe
     */
    Mono<RoleRecord> findByDescription(String description);
}
