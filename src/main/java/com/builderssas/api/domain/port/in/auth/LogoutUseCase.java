package com.builderssas.api.domain.port.in.auth;

import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de logout.
 *
 * Responsabilidad:
 * - Revocar el token activo del usuario.
 *
 * No conoce HTTP.
 * No conoce headers.
 * No usa DTOs web.
 */
public interface LogoutUseCase {

    /**
     * Revoca el token actual.
     *
     * @param rawToken token sin el prefijo Bearer
     * @return Mono vacío cuando el logout finaliza
     */
    Mono<Void> logout(String rawToken);
}
