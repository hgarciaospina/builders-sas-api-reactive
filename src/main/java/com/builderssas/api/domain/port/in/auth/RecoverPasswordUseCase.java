package com.builderssas.api.domain.port.in.auth;

import com.builderssas.api.domain.model.auth.RecoverPasswordRecord;
import reactor.core.publisher.Mono;

/**
 * Puerto de entrada para recuperación de contraseña.
 *
 * No conoce DTOs web.
 */
public interface RecoverPasswordUseCase {

    Mono<RecoverPasswordRecord> recover(String username);
}
