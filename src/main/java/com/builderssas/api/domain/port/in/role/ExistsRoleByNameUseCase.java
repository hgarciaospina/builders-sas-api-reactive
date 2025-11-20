package com.builderssas.api.domain.port.in.role;

import reactor.core.publisher.Mono;

/**
 * Caso de uso para validar si existe un rol según su nombre.
 */
public interface ExistsRoleByNameUseCase {

    /**
     * Verifica existencia del rol por nombre.
     *
     * @param name nombre del rol
     * @return Mono true si existe, false si no
     */
    Mono<Boolean> existsByName(String name);
}
