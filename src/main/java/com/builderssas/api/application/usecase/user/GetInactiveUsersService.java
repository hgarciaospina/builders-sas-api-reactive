package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.GetInactiveUsersUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para listar usuarios desactivados.
 */
@Service
@RequiredArgsConstructor
public class GetInactiveUsersService implements GetInactiveUsersUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Flux<UserRecord> getInactiveUsers() {
        return repository.findAllInactive();
    }
}
