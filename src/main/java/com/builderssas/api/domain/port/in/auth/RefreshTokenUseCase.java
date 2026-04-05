package com.builderssas.api.domain.port.in.auth;

import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para el caso de uso de refresh de token.
 *
 * Responsabilidad:
 * - Validar un REFRESH token persistido.
 * - Emitir un nuevo ACCESS token.
 *
 * No conoce HTTP.
 * No usa DTOs web.
 */
public interface RefreshTokenUseCase {

    /**
     * Ejecuta el refresh del token.
     *
     * @param refreshToken token REFRESH sin el prefijo Bearer
     * @return Mono con el nuevo ACCESS token
     */
    Mono<String> refresh(String refreshToken);
}