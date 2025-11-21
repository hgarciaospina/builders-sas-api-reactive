package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio R2DBC para la tabla "users".
 */
public interface UserR2dbcRepository extends ReactiveCrudRepository<UserEntity, Long> {

    /**
     * Busca un usuario por email.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    Mono<UserEntity> findByEmail(String email);

    /**
     * Busca un usuario por username.
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    Mono<UserEntity> findByUsername(String username);

    /**
     * Lista TODOS los usuarios (activos + inactivos),
     * ordenados por:
     *   - Primero activos (active DESC)
     *   - Luego por username alfabético
     */
    @Query("""
           SELECT * FROM users 
           ORDER BY active DESC, username ASC
           """)
    Flux<UserEntity> findAll();

    /**
     * Lista SOLO usuarios activos.
     */
    @Query("SELECT * FROM users WHERE active = TRUE ORDER BY username ASC")
    Flux<UserEntity> findAllActive();

    /**
     * Lista SOLO usuarios inactivos (soft-delete).
     */
    @Query("SELECT * FROM users WHERE active = FALSE ORDER BY username ASC")
    Flux<UserEntity> findAllInactive();

    /**
     * Borrado lógico.
     */
    @Query("UPDATE users SET active = FALSE WHERE id = :id")
    Mono<Void> softDelete(Long id);
}
