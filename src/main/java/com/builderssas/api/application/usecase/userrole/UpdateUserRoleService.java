package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.UpdateUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Caso de uso para actualizar una relación usuario-rol.
 *
 * Reglas:
 * - Solo se permite actualizar el campo "active".
 * - updatedAt se genera automáticamente.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserRoleService implements UpdateUserRoleUseCase {

    private final UserRoleRepositoryPort repository;

    @Override
    public Mono<UserRoleRecord> update(UserRoleRecord record) {
        return repository.findById(record.id())
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                "No existe un UserRole con id: " + record.id()
                        )
                ))
                // *** ESTA ES LA UNICA LÍNEA NUEVA ***
                .filter(UserRoleRecord::active)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException(
                                "No se puede actualizar porque el UserRole con id "
                                        + record.id() + " está inactivo."
                        )
                ))
                // --------------------------------------------------------
                .map(existing ->
                        new UserRoleRecord(
                                existing.id(),
                                existing.userId(),
                                existing.roleId(),
                                existing.assignedAt(),
                                existing.createdAt(),
                                LocalDateTime.now(),     // updatedAt
                                record.active()          // único campo que cambia
                        )
                )
                .flatMap(repository::save)
                .doOnNext(r ->
                        log.info("UserRole id={} actualizado correctamente.", r.id())
                );
    }
}
