// src/main/java/com/builderssas/api/application/usecase/role/GetRoleByIdService.java
package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.GetRoleByIdUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para obtener un rol por ID.
 */
@Service
@RequiredArgsConstructor
public class GetRoleByIdService implements GetRoleByIdUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("El rol con id " + id + " no existe.")
                ));
    }
}
