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
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Caso de uso: Refresh Token con rotación y soporte multi-rol.
 *
 * Responsabilidades:
 * - Validar REFRESH token persistido
 * - Revocar el REFRESH token usado
 * - Generar nuevo par ACCESS + REFRESH
 * - Persistir ambos tokens
 * - Devolver solo el ACCESS token nuevo
 *
 * Reglas:
 * - 100% funcional y reactivo
 * - Rotación: cada refresh invalida el anterior aunque solo devuelva el access
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
        String cleanToken = refreshToken.trim();

        return tokenRepository.findByToken(cleanToken)
                .filter(this::isValidRefreshToken)
                .switchIfEmpty(Mono.error(
                        new GlobalExceptionHandler.UnauthorizedException("Refresh token inválido o expirado")
                ))
                .flatMap(this::rotateTokensAndReturnAccess);
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
     * Rota tokens: revoca el refresh viejo, genera access + refresh nuevos.
     * Devuelve solo el access nuevo para cumplir el contrato.
     */
    private Mono<String> rotateTokensAndReturnAccess(AuthTokenRecord oldRefresh) {
        // 1. Revocar el refresh usado - mantengo createdAt original
        AuthTokenRecord revoked = new AuthTokenRecord(
                oldRefresh.id(),
                oldRefresh.userId(),
                oldRefresh.token(),
                oldRefresh.tokenType(),
                oldRefresh.issuedAt(),
                oldRefresh.expiresAt(),
                true,
                oldRefresh.createdAt()
        );

        // 2. Resolver usuario + roles para generar tokens nuevos
        return userRepository.findById(oldRefresh.userId())
                .switchIfEmpty(Mono.error(
                        new GlobalExceptionHandler.ResourceNotFoundException("Usuario no encontrado")
                ))
                .flatMap(user -> resolveUserRoles(user.getId())
                        .map(roles -> new AuthenticatedUserRecord(
                                user.getId(),
                                user.getUsername(),
                                roles,
                                user.getDisplayName()
                        ))
                )
                .flatMap(authUser ->
                        // 3. Revocar viejo + generar y guardar ambos, devolver solo access
                        tokenRepository.save(revoked)
                                .then(jwtTokenGenerator.generateAccessToken(authUser))
                                .flatMap(newAccess -> {
                                    LocalDateTime now = LocalDateTime.now(zoneId);
                                    AuthTokenRecord accessRecord = new AuthTokenRecord(
                                            null, authUser.userId(), newAccess, TokenType.ACCESS,
                                            now, now.plusMinutes(15), false, now
                                    );
                                    return tokenRepository.save(accessRecord)
                                            .thenReturn(newAccess); // guardo el access para devolverlo
                                })
                                .zipWhen(access ->
                                        // 4. Generar y guardar el refresh nuevo también, pero no lo devuelvo
                                        jwtTokenGenerator.generateRefreshToken(authUser)
                                                .flatMap(newRefresh -> {
                                                    LocalDateTime now = LocalDateTime.now(zoneId);
                                                    AuthTokenRecord refreshRecord = new AuthTokenRecord(
                                                            null, authUser.userId(), newRefresh, TokenType.REFRESH,
                                                            now, now.plusDays(30), false, now
                                                    );
                                                    return tokenRepository.save(refreshRecord);
                                                })
                                )
                                .map(tuple -> tuple.getT1()) // devuelvo solo el access del primer Mono
                );
    }

    /**
     * Resuelve todos los roles activos de un usuario como lista de strings.
     */
    private Mono<List<String>> resolveUserRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .filter(UserRoleEntity::isActive)
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(RoleEntity::getName)
                .collectList()
                .filter(list -> !list.isEmpty())
                .switchIfEmpty(Mono.just(List.of("ROLE_USER")));
    }
}