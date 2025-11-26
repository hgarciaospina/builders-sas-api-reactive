package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.GetActiveUsersUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para obtener usuarios activos.
 *
 * Reglas:
 * - Solo devuelve usuarios con active = true.
 * - 100% funcional y reactivo.
 * - No hay lógica de negocio adicional.
 */
@Service
@RequiredArgsConstructor
public class GetActiveUsersService implements GetActiveUsersUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Flux<UserRecord> getActiveUsers() {
        return repository.findAllActive();
    }
}
