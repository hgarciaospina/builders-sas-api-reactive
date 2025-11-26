package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.CreateUserUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.infrastructure.web.dto.user.CreateUserDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para creación de usuarios en Builders-SAS.
 *
 * Reglas aplicadas:
 * - El correo debe ser único.
 * - El username debe ser único.
 * - El usuario se crea siempre activo.
 * - Se utiliza UserRecord como modelo de dominio.
 * - 100% programación funcional sin lógica imperativa.
 * - Sin null-checks, sin if/else, sin Optional.
 * - Persistencia siempre a través de UserRepositoryPort.
 */
@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Mono<UserRecord> createUser(CreateUserDto dto) {

        // 1) Validar email único ↓
        Mono<UserRecord> emailCheck =
                repository.findByEmail(dto.email())
                        .flatMap(existing ->
                                Mono.<UserRecord>error(
                                        new DuplicateResourceException("El correo ya está registrado.")
                                )
                        );

        // 2) Validar username único ↓
        Mono<UserRecord> usernameCheck =
                repository.findByUsername(dto.username())
                        .flatMap(existing ->
                                Mono.<UserRecord>error(
                                        new DuplicateResourceException("El nombre de usuario ya está registrado.")
                                )
                        );

        // 3) Crear record de dominio — ejecución diferida (CORRECCIÓN)
        Mono<UserRecord> onCreate =
                Mono.defer(() ->
                        Mono.just(
                                new UserRecord(
                                        null,
                                        dto.username(),
                                        dto.displayName(),
                                        dto.email(),
                                        true
                                )
                        )
                );

        // 4) Pipeline funcional sin imperativa
        return emailCheck
                .switchIfEmpty(usernameCheck)
                .switchIfEmpty(onCreate)
                .flatMap(repository::save);
    }
}
