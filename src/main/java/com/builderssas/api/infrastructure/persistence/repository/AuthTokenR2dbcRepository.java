package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.AuthTokenEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Repositorio R2DBC para la tabla auth_tokens.
 * Infraestructura pura (Spring Data).
 */
public interface AuthTokenR2dbcRepository
        extends ReactiveCrudRepository<AuthTokenEntity, Long> {

    Mono<AuthTokenEntity> findByToken(String token);

    @Query("""
        SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END
        FROM auth_tokens
        WHERE token = :token
          AND revoked = FALSE
          AND expires_at > :now
    """)
    Mono<Boolean> isTokenActive(String token, LocalDateTime now);
}
