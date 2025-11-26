package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.DeleteUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para realizar eliminación lógica (soft delete)
 * de una relación usuario-rol.
 *
 * Reglas:
 * - La eliminación es lógica, nunca física.
 * - createdAt y assignedAt se preservan.
 * - updatedAt se establece al momento de la desactivación.
 * - Si el registro no existe o ya está inactivo, debe lanzar error manejado.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteUserRoleService implements DeleteUserRoleUseCase {

    private final UserRoleRepositoryPort port;

    @Override
    public Mono<Void> delete(Long id) {
        return port.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("No existe la relación UserRole con id: " + id)
                ))
                .filter(UserRoleRecord::active)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("La relación UserRole ya está inactiva. id: " + id)
                ))
                .map(existing ->
                        new UserRoleRecord(
                                existing.id(),
                                existing.userId(),
                                existing.roleId(),
                                existing.assignedAt(),
                                existing.createdAt(),
                                LocalDateTime.now(),
                                Boolean.FALSE
                        )
                )
                .flatMap(port::save)
                .doOnNext(r -> log.info("Soft delete aplicado. id={}", r.id()))
                .then();
    }
}
