// src/main/java/com/builderssas/api/application/usecase/role/ToggleRoleStatusService.java
package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.ToggleRoleStatusUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Implementación del caso de uso para alternar el estado de un rol.
 */
@Service
@RequiredArgsConstructor
public class ToggleRoleStatusService implements ToggleRoleStatusUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> toggleStatus(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("El rol con id " + id + " no existe.")
                ))
                .map(role -> {

                    Boolean current = Optional.ofNullable(role.active())
                            .orElse(Boolean.TRUE);

                    Boolean toggled = Optional.of(current)
                            .filter(Boolean.TRUE::equals)
                            .map(value -> Boolean.FALSE)
                            .orElse(Boolean.TRUE);

                    return new RoleRecord(
                            role.id(),
                            role.name(),
                            role.description(),
                            toggled
                    );
                })
                .flatMap(repository::update);
    }
}
