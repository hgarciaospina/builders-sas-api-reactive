package com.builderssas.api.infrastructure.adapter.security;

import com.builderssas.api.domain.model.auth.AuthenticatedUserRecord;
import com.builderssas.api.domain.port.out.auth.JwtTokenGeneratorPort;
import com.builderssas.api.infrastructure.config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

/**
 * Adapter de infraestructura para la generación de tokens JWT.
 *
 * Implementa el puerto JwtTokenGeneratorPort siguiendo arquitectura hexagonal.
 *
 * Características:
 * - 100% reactivo
 * - Sin block()
 * - Sin estructuras imperativas
 * - Evaluación lazy
 * - Sin uso de tuplas
 *
 * Responsabilidades:
 * - Generar tokens ACCESS y REFRESH
 * - Firmar tokens con HS256
 * - Validar configuración crítica
 */
@Component
public final class JwtTokenGeneratorAdapter implements JwtTokenGeneratorPort {

    private final Mono<Key> signingKey;
    private final Mono<Long> accessExpiration;
    private final Mono<Long> refreshExpiration;

    /**
     * Inicializa configuración reactiva.
     *
     * @param jwtConfig configuración de JWT
     */
    public JwtTokenGeneratorAdapter(JwtConfig jwtConfig) {

        this.signingKey = Mono.justOrEmpty(jwtConfig.getSecretKey())
                .filter(secret -> secret.length() >= 32)
                .flatMap(secret -> Mono.fromSupplier(() ->
                        Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))
                ))
                .cast(Key.class)
                .switchIfEmpty(Mono.error(new IllegalStateException(
                        "JWT_SECRET_KEY inválida. Debe tener al menos 32 caracteres"
                )))
                .cache();

        this.accessExpiration = Mono.just(jwtConfig.getAccessTokenExpirationSeconds())
                .flatMap(value -> validateExpiration(value, "ACCESS"))
                .cache();

        this.refreshExpiration = Mono.just(jwtConfig.getRefreshTokenExpirationSeconds())
                .flatMap(value -> validateExpiration(value, "REFRESH"))
                .cache();
    }

    /**
     * Genera token ACCESS.
     */
    @Override
    public Mono<String> generateAccessToken(AuthenticatedUserRecord user) {
        return signingKey.flatMap(key ->
                accessExpiration.flatMap(expiration ->
                        buildToken(user, key, expiration, "ACCESS")
                )
        );
    }

    /**
     * Genera token REFRESH.
     */
    @Override
    public Mono<String> generateRefreshToken(AuthenticatedUserRecord user) {
        return signingKey.flatMap(key ->
                refreshExpiration.flatMap(expiration ->
                        buildToken(user, key, expiration, "REFRESH")
                )
        );
    }

    /**
     * Construcción funcional del token JWT.
     *
     * @param user usuario autenticado
     * @param key clave de firma
     * @param expiration expiración en segundos
     * @param type tipo de token
     * @return token JWT
     */
    private Mono<String> buildToken(
            AuthenticatedUserRecord user,
            Key key,
            long expiration,
            String type
    ) {

        return Mono.fromSupplier(() -> {

            Instant now = Instant.now();
            Instant expirationTime = now.plusSeconds(expiration);

            return Jwts.builder()
                    .setSubject(String.valueOf(user.userId()))
                    .claim("username", user.username())
                    .claim("fullName", user.fullName())
                    .claim("roles", user.roles())
                    .claim("type", type)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(expirationTime))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        });
    }

    /**
     * Valida que la expiración sea mayor a cero.
     *
     * @param value expiración en segundos
     * @param type tipo de token
     * @return valor validado
     */
    private Mono<Long> validateExpiration(long value, String type) {

        return Mono.just(value)
                .filter(v -> v > 0)
                .switchIfEmpty(Mono.error(new IllegalStateException(
                        "JWT " + type + " expiration debe ser mayor a 0"
                )));
    }
}