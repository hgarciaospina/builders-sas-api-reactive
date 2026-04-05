package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.domain.model.auth.RecoverPasswordRecord;
import com.builderssas.api.domain.port.in.auth.RecoverPasswordUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Caso de uso: Recuperación de contraseña.
 *
 * Responsabilidad:
 * - Generar contraseña temporal segura
 * - Actualizar registro de usuario en AuthUser
 * - Obtener roles activos del usuario
 * - Devolver resultado de recuperación (RecoverPasswordRecord)
 *
 * Todo el flujo es reactivo, funcional y no bloqueante.
 */
@Service
@RequiredArgsConstructor
public class RecoverPasswordService implements RecoverPasswordUseCase {

    private final UserR2dbcRepository userRepository;
    private final AuthUserRepositoryPort authRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;

    @Override
    public Mono<RecoverPasswordRecord> recover(String username) {

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new IllegalStateException("Usuario no encontrado")))
                .flatMap(user -> authRepository.findByUserId(user.getId())
                        .switchIfEmpty(Mono.error(new IllegalStateException("Credenciales no encontradas")))
                        .flatMap(auth -> updateWithTempPassword(auth)
                                .then(resolveRoles(user.getId())
                                        .map(roles -> new RecoverPasswordRecord(
                                                user.getId(),
                                                user.getUsername(),
                                                user.getDisplayName(),
                                                roles,
                                                generateTempPassword()
                                        ))
                                )
                        )
                );
    }

    /**
     * Genera y persiste contraseña temporal.
     */
    private Mono<Void> updateWithTempPassword(AuthUserRecord auth) {
        String tempPassword = generateTempPassword();
        String hashed = BCrypt.hashpw(tempPassword, BCrypt.gensalt());

        AuthUserRecord updated = new AuthUserRecord(
                auth.id(),
                auth.userId(),
                hashed,
                auth.active(),
                auth.createdAt(),
                LocalDateTime.now()
        );

        return authRepository.save(updated).then();
    }

    /**
     * Resuelve todos los roles activos de un usuario como lista de strings.
     */
    private Mono<String> resolveRoles(Long userId) {
        return userRoleRepository.findByUserId(userId)
                .filter(userRole -> userRole.isActive())
                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                .map(role -> role.getName())
                .collectList()
                .filter(list -> !list.isEmpty())
                .map(list -> String.join(",", list)) // si necesitas un string concatenado para token
                .switchIfEmpty(Mono.just("ROLE_SUPPORT")); // rol default si no hay ninguno
    }

    /**
     * Genera contraseña temporal segura (UUID corto).
     */
    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}