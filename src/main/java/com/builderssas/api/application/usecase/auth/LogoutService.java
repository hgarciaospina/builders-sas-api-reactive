package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.port.in.auth.LogoutUseCase;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Caso de uso: Logout.
 *
 * Responsabilidad:
 * - Revocar el token actual de manera server-side
 * - No elimina registros, solo marca revoked = true
 *
 * Flujo funcional:
 * - Reactive, sin bloqueos
 * - No imperativo
 * - No conoce headers ni HTTP
 * - No usa DTOs web
 */
@Service
@RequiredArgsConstructor
public final class LogoutService implements LogoutUseCase {

    private final AuthTokenRepositoryPort tokenRepository;

    /**
     * Revoca el token pasado como parámetro.
     *
     * @param rawToken token JWT a revocar
     * @return Mono vacío indicando finalización
     */
    @Override
    public Mono<Void> logout(String rawToken) {
        return tokenRepository.findByToken(rawToken)
                .switchIfEmpty(Mono.error(new IllegalStateException("Token no encontrado")))
                .flatMap(record -> tokenRepository.save(
                        new AuthTokenRecord(
                                record.id(),
                                record.userId(),
                                record.token(),
                                record.tokenType(),
                                record.issuedAt(),
                                record.expiresAt(),
                                true,                   // revocado
                                LocalDateTime.now()      // updatedAt funcional
                        )
                ))
                .then();
    }
}