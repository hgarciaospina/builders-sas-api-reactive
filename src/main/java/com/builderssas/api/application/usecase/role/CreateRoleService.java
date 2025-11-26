package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.CreateRoleUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para la creación de roles.
 *
 * Reglas del dominio:
 * - active = true en creación.
 * - name debe ser único.
 * - description debe ser única.
 * - 100% reactivo, sin imperativa, sin condicionales booleanos.
 */
@Service
@RequiredArgsConstructor
public class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> create(RoleRecord role) {

        // Normaliza el rol: active siempre TRUE
        RoleRecord normalized = new RoleRecord(
                null,
                role.name(),
                role.description(),
                true
        );

        return repository.findByName(role.name())
                .flatMap(existing -> Mono.error(new DuplicateResourceException(
                        "Ya existe un rol con ese nombre")))
                .switchIfEmpty(
                        repository.findByDescription(role.description())
                                .flatMap(existing -> Mono.error(new DuplicateResourceException(
                                        "Ya existe un rol con esa descripción")))
                )
                .switchIfEmpty(repository.save(normalized))
                .cast(RoleRecord.class);
    }
}
