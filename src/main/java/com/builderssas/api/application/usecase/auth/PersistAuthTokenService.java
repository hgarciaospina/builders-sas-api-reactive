package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.model.enums.TokenType;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Servicio de aplicación para persistir tokens de autenticación.
 *
 * Responsabilidad:
 * - Guardar tokens generados en la capa de persistencia.
 * - Asignar expiración y estado inicial.
 *
 * Diseño:
 * - Totalmente reactivo y funcional.
 * - No conoce capa web ni HTTP.
 * - No contiene lógica de negocio adicional.
 * - Manejo de errores se hace a través de Mono.error en el flujo.
 */
public final class PersistAuthTokenService {

    private final AuthTokenRepositoryPort authTokenRepository;
    private final ZoneId zoneId;
    private final long expirationSeconds;

    public PersistAuthTokenService(
            AuthTokenRepositoryPort authTokenRepository,
            ZoneId zoneId,
            long expirationSeconds
    ) {
        this.authTokenRepository = authTokenRepository;
        this.zoneId = zoneId;
        this.expirationSeconds = expirationSeconds;
    }

    /**
     * Persiste un token de autenticación de manera funcional y reactiva.
     *
     * @param userId    ID del usuario
     * @param token     Token generado
     * @param tokenType Tipo de token (ACCESS o REFRESH)
     * @return Mono con el AuthTokenRecord persistido
     */
    public Mono<AuthTokenRecord> persist(Long userId, String token, TokenType tokenType) {

        LocalDateTime now = LocalDateTime.now(zoneId);

        // Crear registro de token de forma inmutable
        AuthTokenRecord record = new AuthTokenRecord(
                null,                   // ID generado por la base
                userId,                 // ID del usuario
                token,                  // Token generado
                tokenType,              // Tipo de token
                now,                    // Fecha de emisión
                now.plusSeconds(expirationSeconds), // Fecha de expiración
                false,                  // No revocado inicialmente
                now                     // Fecha de creación
        );

        // Persistir y devolver Mono funcional
        return authTokenRepository.save(record);
    }
}