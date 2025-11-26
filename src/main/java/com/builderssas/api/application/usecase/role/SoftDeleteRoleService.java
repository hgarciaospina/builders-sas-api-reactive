// src/main/java/com/builderssas/api/application/usecase/role/SoftDeleteRoleService.java
package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.SoftDeleteRoleUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para realizar borrado lógico de un rol.
 */
@Service
@RequiredArgsConstructor
public class SoftDeleteRoleService implements SoftDeleteRoleUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> softDelete(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("El rol con id " + id + " no existe.")
                ))
                .map(role ->
                        new RoleRecord(
                                role.id(),
                                role.name(),
                                role.description(),
                                Boolean.FALSE
                        )
                )
                .flatMap(repository::update);
    }
}
