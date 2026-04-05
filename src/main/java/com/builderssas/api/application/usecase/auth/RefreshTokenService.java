package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.model.auth.AuthenticatedUserRecord;
import com.builderssas.api.domain.model.enums.TokenType;
import com.builderssas.api.domain.port.in.auth.RefreshTokenUseCase;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import com.builderssas.api.domain.port.out.auth.JwtTokenGeneratorPort;
import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import com.builderssas.api.infrastructure.persistence.entity.UserRoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Caso de uso: Refresh Token con soporte multi-rol.
 *
 * Responsabilidades:
 * - Validar REFRESH token persistido
 * - Revocar el REFRESH token usado
 * - Resolver todos los roles activos del usuario
 * - Generar un nuevo ACCESS token con roles incluidos
 * - Persistir el ACCESS token
 *
 * Reglas:
 * - 100% funcional y reactivo
 * - No contiene lógica HTTP ni DTOs web
 */
@Service
@RequiredArgsConstructor
public final class RefreshTokenService implements RefreshTokenUseCase {

    private final AuthTokenRepositoryPort tokenRepository;
    private final JwtTokenGeneratorPort jwtTokenGenerator;
    private final UserR2dbcRepository userRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;
    private final ZoneId zoneId;

    /**
     * Ejecuta el refresh del token.
     *
     * @param refreshToken token REFRESH sin prefijo Bearer
     * @return Mono con el nuevo ACCESS token
     */
    @Override
    public Mono<String> refresh(String refreshToken) {
        LocalDateTime now = LocalDateTime.now(zoneId);

        return tokenRepository.findByToken(refreshToken)
                // Validar que sea REFRESH válido
                .filter(this::isValidRefreshToken)
                .switchIfEmpty(Mono.error(new IllegalStateException("Refresh token inválido o expirado")))

                // Revocar el REFRESH token usado
                .flatMap(this::revokeRefreshToken)

                // Obtener usuario
                .flatMap(revokedToken -> userRepository.findById(revokedToken.userId())
                        .switchIfEmpty(Mono.error(new IllegalStateException("Usuario no encontrado para refresh")))
                )

                // Resolver roles y construir usuario autenticado
                .flatMap(user -> resolveUserRoles(user.getId())
                        .map(roles -> new AuthenticatedUserRecord(
                                user.getId(),
                                user.getUsername(),
                                roles,
                                user.getDisplayName()
                        ))
                )

                // Generar ACCESS token y persistirlo
                .flatMap(this::generateAndPersistAccessToken)
                .map(AuthTokenRecord::token); // devolver solo el token
    }

    /**
     * Valida que el token sea REFRESH, no revocado y no expirado.
     */
    private boolean isValidRefreshToken(AuthTokenRecord record) {
        LocalDateTime now = LocalDateTime.now(zoneId);
        return !record.revoked()
                && TokenType.REFRESH.equals(record.tokenType())
                && record.expiresAt().isAfter(now);
    }

    /**
     * Revoca el REFRESH token usado.
     *
     * @param record token REFRESH
     * @return Mono con token revocado persistido
     */
    private Mono<AuthTokenRecord> revokeRefreshToken(AuthTokenRecord record) {
        AuthTokenRecord revoked = new AuthTokenRecord(
                record.id(),
                record.userId(),
                record.token(),
                TokenType.REFRESH,
                record.issuedAt(),
                record.expiresAt(),
                true,
                LocalDateTime.now(zoneId)
        );
        return tokenRepository.save(revoked);
    }

    /**
     * Resuelve todos los roles activos de un usuario como lista de strings.
     *
     * @param userId id del usuario
     * @return Mono con lista de nombres de roles
     */
    private Mono<List<String>> resolveUserRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .filter(UserRoleEntity::isActive)
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(RoleEntity::getName)
                .collectList()
                .filter(list -> !list.isEmpty())
                .switchIfEmpty(Mono.just(List.of("ROLE_SUPPORT"))); // rol default si no hay ninguno
    }

    /**
     * Genera un nuevo ACCESS token para el usuario con roles resueltos y lo persiste.
     *
     * @param user usuario autenticado
     * @return Mono con AuthTokenRecord persistido
     */
    private Mono<AuthTokenRecord> generateAndPersistAccessToken(AuthenticatedUserRecord user) {
        LocalDateTime now = LocalDateTime.now(zoneId);

        return jwtTokenGenerator.generateAccessToken(user) // devuelve Mono<String>
                .flatMap(accessToken -> {
                    AuthTokenRecord accessTokenRecord = new AuthTokenRecord(
                            null,
                            user.userId(),
                            accessToken,
                            TokenType.ACCESS,
                            now,
                            now.plusMinutes(60), // duración ACCESS
                            false,
                            now
                    );
                    return tokenRepository.save(accessTokenRecord);
                });
    }
}