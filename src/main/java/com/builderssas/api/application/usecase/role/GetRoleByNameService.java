// src/main/java/com/builderssas/api/application/usecase/role/GetRoleByNameService.java
package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.GetRoleByNameUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para obtener un rol por nombre.
 */
@Service
@RequiredArgsConstructor
public class GetRoleByNameService implements GetRoleByNameUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> findByName(String name) {
        return repository.findByName(name)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("No existe un rol con el nombre proporcionado.")
                ));
    }
}
