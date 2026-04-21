package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthenticatedUserRecord;
import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import com.builderssas.api.domain.port.out.auth.JwtTokenValidatorPort;
import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.util.List;

/**
 * Servicio reactivo de validación de tokens de autenticación.
 *
 * Responsabilidades:
 * - Validar existencia del token en persistencia.
 * - Validar estado del token (revocado/expirado).
 * - Validar que sea token ACCESS.
 * - Resolver información completa del usuario y sus roles.
 *
 * Nota:
 * - Toda excepción que ocurre aquí es transformada en la respuesta HTTP correspondiente
 *   por GlobalExceptionHandler.
 * - No se usan errores genéricos: cada fallo tiene su excepción específica.
 */
@Service
@RequiredArgsConstructor
public final class ValidateAuthTokenService implements JwtTokenValidatorPort {

    private final AuthTokenRepositoryPort tokenRepository;
    private final UserR2dbcRepository userRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;
    private final ZoneId zoneId;

    /**
     * Valida un token ACCESS y devuelve el usuario autenticado con roles.
     *
     * Excepciones:
     * - Si el token no existe, está revocado, expirado o no es REFRESH:
     *   {@link GlobalExceptionHandler.UnauthorizedException} → HTTP 401
     *
     * @param token valor del token a validar
     * @return Mono de usuario autenticado con roles
     */
    @Override
    public Mono<AuthenticatedUserRecord> validateToken(String token) {
        return tokenRepository.findByToken(token)

                // Validar que el token esté vigente (no revocado y no expirado)
                .filter(AuthTokenRecord::isValid)

                // Validar que sea un ACCESS token
                .filter(AuthTokenRecord::isAccessToken)

                // Si falla cualquiera de las validaciones anteriores → error coherente
                .switchIfEmpty(Mono.error(
                        new GlobalExceptionHandler.UnauthorizedException("Access token inválido o expirado")
                ))

                // Resolver usuario autenticado
                .flatMap(this::resolveUser);
    }

    /**
     * Resuelve la información completa del usuario y sus roles.
     *
     * Excepciones:
     * - Usuario no encontrado → {@link GlobalExceptionHandler.ResourceNotFoundException} → HTTP 404
     *
     * @param tokenRecord registro del token válido
     * @return Mono de usuario autenticado con roles
     */
    private Mono<AuthenticatedUserRecord> resolveUser(AuthTokenRecord tokenRecord) {
        return userRepository.findById(tokenRecord.userId())
                .switchIfEmpty(Mono.error(
                        new GlobalExceptionHandler.ResourceNotFoundException("Usuario no encontrado")
                ))
                .flatMap(user -> resolveRoles(user.getId())
                        .map(roles -> new AuthenticatedUserRecord(
                                user.getId(),
                                user.getUsername(),
                                roles,
                                user.getDisplayName()
                        )));
    }

    /**
     * Obtiene todos los nombres de roles activos asociados al usuario.
     *
     * Excepciones:
     * - Si algún rol asociado no existe → {@link GlobalExceptionHandler.ResourceNotFoundException} → HTTP 404
     *
     * @param userId id del usuario
     * @return Mono con lista de nombres de roles activos
     */
    private Mono<List<String>> resolveRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .filter(userRole -> Boolean.TRUE.equals(userRole.isActive()))
                .flatMap(userRole ->
                        roleRepository.findById(userRole.getRoleId())
                                .switchIfEmpty(Mono.error(
                                        new GlobalExceptionHandler.ResourceNotFoundException(
                                                "Rol no encontrado para el usuario ID: " + userId
                                        )
                                ))
                )
                .map(RoleEntity::getName)
                .collectList();
    }
}