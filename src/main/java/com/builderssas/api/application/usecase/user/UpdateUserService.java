package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.UpdateUserUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.infrastructure.web.dto.user.UpdateUserDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso encargado de actualizar la información de un usuario existente.
 */
@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Mono<UserRecord> updateUser(UpdateUserDto dto) {

        return repository.findById(dto.id())

                // 1) Si el usuario no existe → error
                .switchIfEmpty(
                        Mono.<UserRecord>error(
                                new ResourceNotFoundException("El usuario no existe.")
                        )
                )

                // 2) Validar email único
                .flatMap(existing ->
                        repository.findByEmail(dto.email())
                                .filter(found -> !found.id().equals(dto.id()))
                                .flatMap(found ->
                                        Mono.<UserRecord>error(
                                                new DuplicateResourceException(
                                                        "El correo ya está registrado por otro usuario."
                                                )
                                        )
                                )

                                // 3) Validar username único
                                .switchIfEmpty(
                                        repository.findByUsername(dto.username())
                                                .filter(found -> !found.id().equals(dto.id()))
                                                .flatMap(found ->
                                                        Mono.<UserRecord>error(
                                                                new DuplicateResourceException(
                                                                        "El nombre de usuario ya está registrado por otro usuario."
                                                                )
                                                        )
                                                )
                                )

                                // 4) Si pasa todos los filtros → actualizar usuario
                                .switchIfEmpty(
                                        repository.update(
                                                new UserRecord(
                                                        dto.id(),
                                                        dto.username(),
                                                        dto.displayName(),
                                                        dto.email(),
                                                        dto.active()
                                                )
                                        )
                                )
                );
    }
}
