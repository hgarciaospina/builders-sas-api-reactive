package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.port.in.auth.ChangePasswordUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;

import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;

import com.builderssas.api.infrastructure.web.dto.auth.ChangePasswordDto;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;

@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

    private final AuthUserRepositoryPort authRepository;
    private final UserR2dbcRepository userRepository;

    @Override
    public Mono<Void> changePassword(ChangePasswordDto dto) {

        return userRepository.findById(dto.userId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuario no encontrado")))

                .flatMap(user ->
                        authRepository.findByUserId(dto.userId())
                                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credenciales no encontradas")))

                                // ✔ Comparación correcta usando BCrypt
                                .filter(auth -> BCrypt.checkpw(dto.oldPassword(), auth.passwordHash()))
                                .switchIfEmpty(Mono.error(new UnauthorizedException("Contraseña actual incorrecta")))

                                // ✔ Hash de nueva contraseña
                                .map(auth ->
                                        new com.builderssas.api.domain.model.auth.AuthUserRecord(
                                                auth.id(),
                                                auth.userId(),
                                                BCrypt.hashpw(dto.newPassword(), BCrypt.gensalt()), // ← único cambio
                                                auth.active(),
                                                auth.createdAt(),
                                                LocalDateTime.now()
                                        )
                                )

                                .flatMap(authRepository::save)
                                .then()
                );
    }
}
