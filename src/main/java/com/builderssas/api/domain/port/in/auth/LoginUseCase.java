package com.builderssas.api.domain.port.in.auth;

import com.builderssas.api.domain.model.auth.AuthSessionRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso de login.
 */
public interface LoginUseCase {

    /**
     * Ejecuta autenticación.
     *
     * @return sesión autenticada con tokens
     */
    Mono<AuthSessionRecord> login(String username, String password);
}