package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.GetAllUsersUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Servicio encargado de listar todos los usuarios activos del sistema.
 */
@Service
@RequiredArgsConstructor
public class GetAllUsersService implements GetAllUsersUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Flux<UserRecord> getAll() {
        return repository.findAll();
    }
}
