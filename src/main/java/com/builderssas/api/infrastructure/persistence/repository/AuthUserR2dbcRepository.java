package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.AuthUserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repositorio R2DBC para la tabla auth_users.
 *
 */
@Repository
public interface AuthUserR2dbcRepository extends R2dbcRepository<AuthUserEntity, Long> {

    /**
     * Busca credenciales por ID de usuario.
     */
    Mono<AuthUserEntity> findByUserId(Long userId);
}
