package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.RemoveUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementación del caso de uso para desactivar una relación usuario-rol.
 *
 * Esta clase pertenece a la capa de Aplicación dentro de la Arquitectura Hexagonal.
 * Su responsabilidad es realizar un "soft delete" marcando el registro como inactivo.
 *
 * Decisiones de diseño:
 * - Se conserva userId, roleId y assignedAt del registro existente.
 * - createdAt permanece intacto.
 * - updatedAt se actualiza con la hora actual.
 * - active cambia a false, representando desactivación lógica.
 *
 * Este enfoque garantiza trazabilidad temporal y evita pérdida de historial.
 */
@RequiredArgsConstructor
@Slf4j
public class RemoveUserRoleUseCaseService implements RemoveUserRoleUseCase {

    /**
     * Puerto de salida responsable de recuperar y persistir asignaciones usuario-rol.
     */
    private final UserRoleRepositoryPort repository;

    /**
     * Marca una asignación usuario-rol como inactiva, efectuando un "soft delete".
     *
     * @param id identificador de la asignación.
     * @return Mono<Void> indicando que la operación fue completada exitosamente.
     */
    @Override
    public Mono<Void> remove(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalStateException("Asignación no encontrada: " + id)))
                .map(existing ->
                        new UserRoleRecord(
                                existing.id(),
                                existing.userId(),
                                existing.roleId(),
                                existing.assignedAt(),   // se mantiene trazabilidad original
                                existing.createdAt(),    // se conserva creación original
                                LocalDateTime.now(),     // updatedAt actualizado
                                false                    // activación → false
                        )
                )
                .doOnNext(r ->
                        log.info("Desactivando asignación usuario-rol con id {}", r.id()))
                .flatMap(repository::save)
                .then();
    }
}
