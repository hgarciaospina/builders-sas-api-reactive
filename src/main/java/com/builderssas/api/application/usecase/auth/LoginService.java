package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthSessionRecord;
import com.builderssas.api.domain.model.auth.AuthenticatedUserRecord;
import com.builderssas.api.domain.model.auth.AuthTokenRecord;
import com.builderssas.api.domain.model.enums.TokenType;
import com.builderssas.api.domain.port.in.auth.LoginUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.domain.port.out.auth.AuthTokenRepositoryPort;
import com.builderssas.api.domain.port.out.auth.JwtTokenGeneratorPort;

import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Caso de uso de login (autenticación de usuarios).
 *
 * Responsabilidad:
 * - Validar credenciales de usuario
 * - Resolver roles asignados
 * - Generar tokens de acceso (ACCESS) y refresco (REFRESH)
 * - Persistir tokens en base de datos
 *
 * Características:
 * - 100% funcional y reactivo
 * - Compatible con DDD y arquitectura hexagonal
 * - No contiene lógica de infraestructura ni imperativa
 */
@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final AuthUserRepositoryPort authRepository;
    private final UserR2dbcRepository userRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;
    private final JwtTokenGeneratorPort jwtTokenGenerator;
    private final AuthTokenRepositoryPort authTokenRepository;
    private final ZoneId zoneId;

    @Override
    public Mono<AuthSessionRecord> login(String username, String password) {
        LocalDateTime now = LocalDateTime.now(zoneId);

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new IllegalStateException("Usuario no encontrado")))

                // Validar credenciales
                .flatMap(user -> authRepository.findByUserId(user.getId())
                        .switchIfEmpty(Mono.error(new IllegalStateException("Credenciales no encontradas")))
                        .filter(auth -> org.mindrot.jbcrypt.BCrypt.checkpw(password, auth.passwordHash()))
                        .switchIfEmpty(Mono.error(new SecurityException("Contraseña incorrecta")))
                        .map(auth -> user)
                )

                // Obtener roles de manera funcional
                .flatMap(user -> userRoleRepository.findByUserId(user.getId())
                        .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                        .map(RoleEntity::getName)
                        .collectList()
                        .filter(roleNames -> !roleNames.isEmpty())
                        .switchIfEmpty(Mono.error(new IllegalStateException("No se encontraron roles asignados")))
                        .map(roleNames -> new AuthenticatedUserRecord(
                                user.getId(),
                                user.getUsername(),
                                roleNames,
                                user.getDisplayName()
                        ))
                )

                // Generar tokens de forma 100% reactiva
                .flatMap(authUser ->
                        jwtTokenGenerator.generateAccessToken(authUser)
                                .flatMap(accessToken ->
                                        jwtTokenGenerator.generateRefreshToken(authUser)
                                                .flatMap(refreshToken -> {

                                                    // Crear registros de tokens
                                                    AuthTokenRecord accessTokenRecord = new AuthTokenRecord(
                                                            null,
                                                            authUser.userId(),
                                                            accessToken,
                                                            TokenType.ACCESS,
                                                            now,
                                                            now.plusMinutes(15),
                                                            false,
                                                            now
                                                    );

                                                    AuthTokenRecord refreshTokenRecord = new AuthTokenRecord(
                                                            null,
                                                            authUser.userId(),
                                                            refreshToken,
                                                            TokenType.REFRESH,
                                                            now,
                                                            now.plusDays(7),
                                                            false,
                                                            now
                                                    );

                                                    // Guardar tokens en paralelo
                                                    return Mono.when(
                                                                    authTokenRepository.save(accessTokenRecord),
                                                                    authTokenRepository.save(refreshTokenRecord)
                                                            )
                                                            .thenReturn(new AuthSessionRecord(authUser, accessToken, refreshToken));
                                                })
                                )
                );
    }
}