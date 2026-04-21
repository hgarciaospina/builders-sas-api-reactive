package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.port.in.auth.LogoutUseCase;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso: Logout.
 *
 * Responsabilidad:
 * - Revocar el token actual de manera server-side
 * - Idempotente: si no existe, completa sin error
 *
 * Flujo funcional:
 * - Reactive, sin bloqueos
 * - Limpia espacios/saltos de línea del token antes de buscar
 * - No conoce headers ni HTTP
 * - No usa DTOs web
 */
@Service
@RequiredArgsConstructor
@Slf4j
public final class LogoutService implements LogoutUseCase {

    private final AuthTokenRepositoryPort tokenRepository;

    /**
     * Revoca el token pasado como parámetro.
     * Idempotente: si no existe, completa vacío devolviendo 200 OK.
     *
     * @param rawToken token JWT a revocar, puede venir con espacios o \n
     * @return Mono vacío indicando finalización
     */
    @Override
    public Mono<Void> logout(String rawToken) {
        String cleanToken = rawToken.trim().replaceAll("\\s+", "");
        log.info("LogoutService.logout() - Iniciando. Token recibido largo={}", rawToken.length());
        log.info("LogoutService.logout() - Token limpio largo={}", cleanToken.length());

        return tokenRepository.findByToken(cleanToken)
                .doOnSubscribe(s -> log.info("LogoutService - Buscando token en BD..."))
                .doOnNext(record -> log.info("LogoutService - Token ENCONTRADO: id={}, userId={}, tipo={}, revoked={}",
                        record.id(), record.userId(), record.tokenType(), record.revoked()))
                .switchIfEmpty(Mono.fromRunnable(() -> log.error("LogoutService - TOKEN NO ENCONTRADO EN BD. No se revoca nada.")))
                .flatMap(record -> {
                    log.info("LogoutService - Actualizando token id={} a revoked=true", record.id());
                    return tokenRepository.save(
                            new AuthTokenRecord(
                                    record.id(),           // Long id
                                    record.userId(),       // Long userId
                                    record.token(),        // String token
                                    record.tokenType(),    // TokenType tokenType
                                    record.issuedAt(),     // LocalDateTime issuedAt
                                    record.expiresAt(),    // LocalDateTime expiresAt
                                    true,                  // boolean revoked = true
                                    record.createdAt()     // LocalDateTime createdAt
                            )
                    );
                })
                .doOnSuccess(saved -> log.info("LogoutService - Save ejecutado. Token id={} guardado con revoked={}",
                        saved.id(), saved.revoked()))
                .doOnError(e -> log.error("LogoutService - ERROR en save o findByToken", e))
                .then()
                .doOnSuccess(v -> log.info("LogoutService.logout() - Finalizado"));
    }
}