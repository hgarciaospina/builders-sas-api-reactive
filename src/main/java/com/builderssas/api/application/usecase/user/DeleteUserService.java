package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.port.in.user.DeleteUserUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import com.builderssas.api.infrastructure.web.dto.user.DeleteUserDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio encargado de realizar el borrado lógico de usuarios.
 */
@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Mono<Void> deleteUser(DeleteUserDto dto) {
        return repository.findById(dto.id())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El usuario no existe.")))
                .flatMap(existing -> repository.softDelete(dto.id()));
    }
}

