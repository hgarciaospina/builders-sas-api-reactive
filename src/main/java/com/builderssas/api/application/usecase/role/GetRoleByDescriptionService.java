// src/main/java/com/builderssas/api/application/usecase/role/GetRoleByDescriptionService.java
package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.GetRoleByDescriptionUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implementación del caso de uso para obtener un rol por descripción.
 */
@Service
@RequiredArgsConstructor
public class GetRoleByDescriptionService implements GetRoleByDescriptionUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> findByDescription(String description) {
        return repository.findByDescription(description)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("No existe un rol con la descripción proporcionada.")
                ));
    }
}
