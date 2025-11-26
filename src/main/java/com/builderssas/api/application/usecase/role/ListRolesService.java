package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.ListRolesUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para listar todos los roles.
 */
@Service
@RequiredArgsConstructor
public class ListRolesService implements ListRolesUseCase {

    private final RoleRepositoryPort repository;
    @Override
    public Flux<RoleRecord> listAll() {
        return repository.findAll();
    }
}
