package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.UpdateRoleUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para actualizar un rol existente.
 *
 * Reglas funcionales:
 * - El rol debe existir antes de actualizar.
 * - name no puede duplicarse con otro rol.
 * - description no puede duplicarse con otro rol.
 * - active NO se modifica aquí (solo en toggleStatusUseCase).
 * - 100% reactivo, sin imperativa.
 */
@Service
@RequiredArgsConstructor
public class UpdateRoleService implements UpdateRoleUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> update(Long id, RoleRecord role) {

        // Normalización: active NO se toca aquí → queda null y luego
        // se sustituye por el valor verdadero que ya tiene el registro.
        RoleRecord normalized = new RoleRecord(
                id,
                role.name(),
                role.description(),
                null
        );

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                        "El rol con id " + id + " no existe."
                )))

                // Validación de nombre duplicado
                .flatMap(existing ->
                        repository.findByName(role.name())
                                .flatMap(found ->
                                        Mono.just(found)
                                                .filter(f -> f.id().equals(id))
                                                .switchIfEmpty(Mono.error(
                                                        new DuplicateResourceException("Ya existe un rol con ese nombre")
                                                ))
                                )
                                .switchIfEmpty(Mono.just(existing))
                )

                // Validación de descripción duplicada
                .flatMap(existing ->
                        repository.findByDescription(role.description())
                                .flatMap(found ->
                                        Mono.just(found)
                                                .filter(f -> f.id().equals(id))
                                                .switchIfEmpty(Mono.error(
                                                        new DuplicateResourceException("Ya existe un rol con esa descripción")
                                                ))
                                )
                                .switchIfEmpty(Mono.just(existing))
                )

                // Actualización final manteniendo el active original
                .flatMap(existing ->
                        repository.update(
                                new RoleRecord(
                                        existing.id(),
                                        normalized.name(),
                                        normalized.description(),
                                        existing.active() // SE CONSERVA el estado real
                                )
                        )
                );
    }
}
