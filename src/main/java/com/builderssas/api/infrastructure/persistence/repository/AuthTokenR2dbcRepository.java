package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.AuthTokenEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Repositorio R2DBC para la entidad AuthTokenEntity.
 * <p>
 * Forma parte de la capa de infraestructura dentro de la arquitectura hexagonal.
 * Su responsabilidad es proveer acceso reactivo a la tabla auth_tokens.
 * <p>
 * Responsabilidades:
 * - Acceso reactivo a tokens de autenticación
 * - Consultas especializadas para validación de seguridad
 * - Operaciones de lectura optimizadas sobre tokens
 * <p>
 * Notas de diseño:
 * - Se evita lógica de negocio en este componente.
 * - Las consultas personalizadas se expresan mediante @Query cuando es necesario.
 * - Hereda operaciones CRUD reactivas estándar desde ReactiveCrudRepository.
 * - No se implementa lógica de actualización aquí; esto se delega a capas superiores
 * o a métodos específicos cuando sea estrictamente necesario.
 */
public interface AuthTokenR2dbcRepository
        extends ReactiveCrudRepository<AuthTokenEntity, Long> {

    /**
     * Busca un token de autenticación por su valor exacto.
     *
     * @param token token JWT en formato string
     * @return entidad correspondiente si existe, Mono vacío en caso contrario
     */
    @Query("""
                SELECT *
                FROM auth_tokens
                WHERE TRIM(token) = TRIM(:token)
            """)
    Mono<AuthTokenEntity> findByToken(String token);

    /**
     * Verifica si un token se encuentra activo.
     * <p>
     * Un token se considera activo cuando:
     * - No está revocado
     * - No ha expirado
     *
     * @param token token JWT en formato string
     * @param now   fecha y hora actual del servidor
     * @return true si el token es válido, false en caso contrario
     */
    @Query("""
                SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END
                FROM auth_tokens
                WHERE token = :token
                  AND revoked = FALSE
                  AND expires_at > :now
            """)
    Mono<Boolean> isTokenActive(String token, LocalDateTime now);
}