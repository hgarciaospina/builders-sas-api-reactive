package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.application.exception.DataInconsistencyException;
import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.domain.port.in.auth.ChangePasswordUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.web.dto.auth.ChangePasswordDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
 * - Propagar correctamente inconsistencias de datos únicas (email, username)
 *   para que el GlobalExceptionHandler devuelva 422.
 *
 * Diseño:
 * - 100% reactivo y funcional (WebFlux).
 * - Hexagonal y separado de DTOs web.
 * - No bloquea hilos.
 * - Inmutabilidad total.
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
     * 2. Propagar errores de múltiples registros (422).
     * 3. Verificar existencia de credenciales.
     * 4. Validar la contraseña actual usando BCrypt.
     * 5. Mapear a un nuevo AuthUserRecord inmutable con la nueva contraseña hasheada.
     * 6. Persistir cambios.
     *
     * @param dto DTO con userId, oldPassword y newPassword
     * @return Mono<Void> que completa cuando la operación finaliza
     */
    @Override
    public Mono<Void> changePassword(ChangePasswordDto dto) {
        return userRepository.findById(dto.userId())
                // Propagar error de múltiples registros a DataInconsistencyException → 422
                .onErrorMap(IncorrectResultSizeDataAccessException.class,
                        ex -> new DataInconsistencyException(
                                "Inconsistencia de datos: múltiples registros encontrados para userId: " + dto.userId()
                        ))
                // Usuario no encontrado → 404
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Usuario no encontrado con id: " + dto.userId())
                ))
                .flatMap(user ->
                        authRepository.findByUserId(dto.userId())
                                .onErrorMap(IncorrectResultSizeDataAccessException.class,
                                        ex -> new DataInconsistencyException(
                                                "Inconsistencia de datos: múltiples credenciales encontradas para userId: " + dto.userId()
                                        ))
                                .switchIfEmpty(Mono.error(
                                        new ResourceNotFoundException("Credenciales no encontradas para usuario: " + dto.userId())
                                ))
                                // Validar contraseña actual
                                .filter(auth -> BCrypt.checkpw(dto.oldPassword(), auth.passwordHash()))
                                .switchIfEmpty(Mono.error(
                                        new UnauthorizedException("Contraseña actual incorrecta")
                                ))
                                // Mapear a un nuevo AuthUserRecord con la contraseña hasheada
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