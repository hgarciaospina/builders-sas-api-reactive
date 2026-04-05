package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.model.auth.AuthUserRecord;
import com.builderssas.api.domain.port.in.auth.CreateAuthUserUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;
import com.builderssas.api.infrastructure.web.dto.auth.CreateAuthUserDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para la creación de credenciales de usuario (AuthUser).
 *
 * Responsabilidad:
 * - Persistir un AuthUserRecord con contraseña hasheada.
 * - Validar existencia del usuario antes de crear credenciales.
 *
 * Reglas de diseño:
 * - Todo el flujo es reactivo, 100% funcional, no bloqueante.
 * - No realiza operaciones HTTP ni expone DTOs web fuera de la capa de entrada.
 * - No genera tokens.
 */
@Service
@RequiredArgsConstructor
public final class CreateAuthUserService implements CreateAuthUserUseCase {

    private final UserR2dbcRepository userRepository;
    private final AuthUserRepositoryPort authRepository;

    /**
     * Crea las credenciales de un usuario dado un DTO de entrada.
     *
     * Flujo funcional:
     * 1. Busca el usuario por ID en UserR2dbcRepository.
     * 2. Si no existe, lanza un Mono.error con ResourceNotFoundException.
     * 3. Hashea la contraseña y construye un AuthUserRecord.
     * 4. Persiste el AuthUserRecord usando AuthUserRepositoryPort.
     *
     * @param dto DTO con userId y contraseña
     * @return Mono<Void> que completa cuando el registro se guarda
     */
    @Override
    public Mono<Void> create(CreateAuthUserDto dto) {
        return userRepository.findById(dto.userId())
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Usuario no encontrado con id: " + dto.userId())
                ))
                // Construye AuthUserRecord de manera funcional
                .map(user -> buildAuthUserRecord(user.getId(), dto.password()))
                // Persiste en el repositorio de AuthUser
                .flatMap(authRepository::save)
                // Mono<Void> indicando finalización
                .then();
    }

    /**
     * Construye un AuthUserRecord a partir del userId y contraseña sin procesar.
     *
     * @param userId       ID del usuario
     * @param rawPassword  Contraseña sin hashear
     * @return AuthUserRecord listo para persistencia
     */
    private AuthUserRecord buildAuthUserRecord(Long userId, String rawPassword) {
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        LocalDateTime now = LocalDateTime.now();
        return new AuthUserRecord(
                null,       // id generado por persistencia
                userId,
                hashed,
                true,   // activo por defecto
                now,           // createdAt
                now            // updatedAt
        );
    }
}