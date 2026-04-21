package com.builderssas.api.application.support.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.model.enums.TokenType;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Servicio de soporte de la capa de aplicación para persistir tokens de autenticación.
 *
 * Responsabilidades:
 * - Persistir tokens generados (ACCESS o REFRESH) en la capa de persistencia.
 * - Crear registros inmutables de tokens con información completa:
 *   usuario, tipo, fecha de emisión, expiración y estado inicial no revocado.
 * - Garantizar que no se mezcle lógica de negocio adicional ni conocimientos de la capa web/HTTP.
 *
 * Diseño:
 * - Totalmente reactivo y funcional.
 * - No implementa casos de uso, por lo que se ubica en support.auth.
 * - Manejo de errores se realiza a través de Mono.error en el flujo.
 *
 * Ejemplo de uso:
 * PersistAuthTokenService service = new PersistAuthTokenService(authTokenRepository, ZoneId.systemDefault(), 3600);
 * Mono<AuthTokenRecord> tokenMono = service.persist(userId, jwtToken, TokenType.ACCESS);
 */
public final class PersistAuthTokenService {

    private final AuthTokenRepositoryPort authTokenRepository;
    private final ZoneId zoneId;
    private final long expirationSeconds;

    /**
     * Constructor del servicio.
     *
     * @param authTokenRepository puerto de persistencia de tokens
     * @param zoneId zona horaria para calcular expiración
     * @param expirationSeconds duración del token en segundos
     */
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
     * Persiste un token de autenticación.
     *
     * Crea un registro inmutable de AuthTokenRecord con todos los datos necesarios
     * del token, incluyendo fecha de emisión, expiración y estado inicial no revocado.
     *
     * @param userId ID del usuario al que pertenece el token
     * @param token valor del token JWT generado
     * @param tokenType tipo de token (ACCESS o REFRESH)
     * @return Mono con el AuthTokenRecord persistido
     */
    public Mono<AuthTokenRecord> persist(Long userId, String token, TokenType tokenType) {

        LocalDateTime now = LocalDateTime.now(zoneId);

        AuthTokenRecord record = new AuthTokenRecord(
                null,                // ID generado por la base de datos
                userId,                 // ID del usuario
                token,                  // Valor del token JWT
                tokenType,              // Tipo de token
                now,                    // Fecha de emisión
                now.plusSeconds(expirationSeconds), // Fecha de expiración
                false,                  // No revocado inicialmente
                now                     // Fecha de creación
        );

        return authTokenRepository.save(record);
    }
}