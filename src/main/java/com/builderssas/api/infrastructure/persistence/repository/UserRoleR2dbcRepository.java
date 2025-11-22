package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.UserRoleEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio R2DBC para la tabla "user_roles".
 *
 * Sigue exactamente el estándar utilizado en UserR2dbcRepository:
 *  - findAll() trae activos + inactivos
 *  - findAllActive() solo activos
 *  - findAllInactive() solo inactivos
 *  - softDelete(id) realiza borrado lógico
 */
public interface UserRoleR2dbcRepository extends ReactiveCrudRepository<UserRoleEntity, Long> {

    /**
     * Busca asignaciones usuario-rol por user_id.
     */
    @Query("SELECT * FROM user_roles WHERE user_id = :userId")
    Flux<UserRoleEntity> findByUserId(Long userId);

    /**
     * Busca una asignación usuario-rol específica por usuario y rol.
     */
    @Query("""
           SELECT * FROM user_roles 
           WHERE user_id = :userId AND role_id = :roleId
           LIMIT 1
           """)
    Mono<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * Lista TODOS (activos + inactivos)
     */
    @Query("SELECT * FROM user_roles ORDER BY active DESC, user_id ASC")
    Flux<UserRoleEntity> findAll();

    /**
     * Lista SOLO activos.
     */
    @Query("SELECT * FROM user_roles WHERE active = TRUE ORDER BY user_id ASC")
    Flux<UserRoleEntity> findAllActive();

    /**
     * Lista SOLO inactivos.
     */
    @Query("SELECT * FROM user_roles WHERE active = FALSE ORDER BY user_id ASC")
    Flux<UserRoleEntity> findAllInactive();

    /**
     * Borrado lógico.
     */
    @Query("UPDATE user_roles SET active = FALSE WHERE id = :id")
    Mono<Void> softDelete(Long id);
}
