package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.domain.port.in.auth.ChangePasswordUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.web.dto.auth.ChangePasswordDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Caso de uso de cambio de contraseña.
 *
 * Responsabilidad:
 * - Validar existencia de usuario y credenciales.
 * - Verificar la contraseña actual.
 * - Hashear la nueva contraseña.
 * - Persistir el AuthUserRecord actualizado.
 *
 * Diseño:
 * - 100% reactivo y funcional (WebFlux).
 * - Hexagonal y separado de DTOs web.
 * - No bloquea hilos.
 * - No expone la contraseña en ningún momento.
 */
@Service
@RequiredArgsConstructor
public final class ChangePasswordService implements ChangePasswordUseCase {

    private final AuthUserRepositoryPort authRepository;
    private final UserR2dbcRepository userRepository;

    /**
     * Cambia la contraseña de un usuario.
     *
     * Flujo funcional:
     * 1. Buscar usuario por ID.
     * 2. Verificar que existan credenciales.
     * 3. Validar la contraseña actual usando BCrypt.
     * 4. Construir nuevo AuthUserRecord con la nueva contraseña hasheada.
     * 5. Persistir el registro actualizado.
     *
     * @param dto DTO con userId, oldPassword y newPassword
     * @return Mono<Void> que completa cuando la operación finaliza
     */
    @Override
    public Mono<Void> changePassword(ChangePasswordDto dto) {
        return userRepository.findById(dto.userId())
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Usuario no encontrado con id: " + dto.userId())
                ))
                .flatMap(user -> authRepository.findByUserId(dto.userId())
                        .switchIfEmpty(Mono.error(
                                new ResourceNotFoundException("Credenciales no encontradas para usuario: " + dto.userId())
                        ))
                        // Validar la contraseña actual
                        .filter(auth -> BCrypt.checkpw(dto.oldPassword(), auth.passwordHash()))
                        .switchIfEmpty(Mono.error(
                                new UnauthorizedException("Contraseña actual incorrecta")
                        ))
                        // Mapear a nuevo AuthUserRecord con la nueva contraseña hasheada
                        .map(auth -> hashNewPassword(auth, dto.newPassword()))
                        // Persistir cambios
                        .flatMap(authRepository::save)
                        .then()
                );
    }

    /**
     * Genera un nuevo AuthUserRecord con la contraseña actualizada.
     *
     * @param auth AuthUserRecord original
     * @param newPassword contraseña en texto plano
     * @return nuevo AuthUserRecord con contraseña hasheada
     */
    private AuthUserRecord hashNewPassword(AuthUserRecord auth, String newPassword) {
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return new AuthUserRecord(
                auth.id(),
                auth.userId(),
                hashed,
                auth.active(),
                auth.createdAt(),
                LocalDateTime.now() // updatedAt
        );
    }
}