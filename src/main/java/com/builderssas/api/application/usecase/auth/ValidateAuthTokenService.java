package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthenticatedUserRecord;
import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import com.builderssas.api.domain.port.out.auth.JwtTokenValidatorPort;

import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Caso de uso de validación de token de autenticación.
 *
 * Responsabilidad:
 * - Validar existencia y estado del token
 * - Resolver el usuario asociado
 * - Resolver los roles del usuario (multi-rol)
 * - Construir el modelo de dominio autenticado
 *
 * No conoce HTTP.
 * No conoce JWT internamente.
 * No contiene lógica de infraestructura.
 */
@Service
@RequiredArgsConstructor
public final class ValidateAuthTokenService implements JwtTokenValidatorPort {

    private final AuthTokenRepositoryPort tokenRepository;
    private final UserR2dbcRepository userRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;
    private final ZoneId zoneId;

    @Override
    public Mono<AuthenticatedUserRecord> validateToken(String token) {

        return tokenRepository.findByToken(token)
                .filter(this::isValidToken)
                .switchIfEmpty(Mono.error(new IllegalStateException("Token inválido o expirado")))
                .flatMap(this::resolveUser);
    }

    /**
     * Valida que el token no esté revocado y no haya expirado.
     */
    private boolean isValidToken(AuthTokenRecord record) {
        return !record.revoked()
                && record.expiresAt().isAfter(LocalDateTime.now(zoneId));
    }

    /**
     * Resuelve la información completa del usuario autenticado.
     */
    private Mono<AuthenticatedUserRecord> resolveUser(AuthTokenRecord tokenRecord) {

        return userRepository.findById(tokenRecord.userId())
                .switchIfEmpty(Mono.error(new IllegalStateException("Usuario no encontrado")))
                .flatMap(user ->
                        resolveRoles(user.getId())
                                .map(roles -> new AuthenticatedUserRecord(
                                        user.getId(),
                                        user.getUsername(),
                                        roles,
                                        user.getDisplayName()
                                ))
                );
    }

    /**
     * Resuelve todos los roles activos del usuario.
     */
    private Mono<List<String>> resolveRoles(Long userId) {

        return userRoleRepository.findByUserId(userId)
                .filter(userRole -> Boolean.TRUE.equals(userRole.isActive()))
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(role -> role.getName())
                .collectList();
    }
}