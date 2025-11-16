package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.UserRoleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repositorio R2DBC para la entidad {@link UserRoleEntity}.
 *
 * Este componente pertenece a la capa de infraestructura dentro
 * de la Arquitectura Hexagonal y define directamente las operaciones
 * reactivas soportadas por Spring Data R2DBC sobre la tabla "user_roles".
 *
 * Características:
 *  • Implementación automática (Spring genera todo el código).
 *  • Totalmente reactivo (Flux/Mono).
 *  • Sin lógica de negocio — solo acceso a la BD.
 *
 * Consultas útiles:
 *  • findByUserId  – Obtener los roles de un usuario.
 *  • findByRoleId  – Obtener todos los usuarios con un rol.
 */
@Repository
public interface UserRoleR2dbcRepository extends R2dbcRepository<UserRoleEntity, Long> {

    /**
     * Obtiene todas las relaciones usuario-rol asignadas a un usuario específico.
     *
     * @param userId ID del usuario.
     * @return Flux con todas las asignaciones.
     */
    Flux<UserRoleEntity> findByUserId(Long userId);

    /**
     * Obtiene todas las asignaciones donde aparece un rol específico.
     *
     * @param roleId ID del rol.
     * @return Flux con todas las asignaciones.
     */
    Flux<UserRoleEntity> findByRoleId(Long roleId);
}
