package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.DeleteUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para realizar eliminación lógica (soft delete)
 * de una relación usuario-rol.
 *
 * Este servicio pertenece a la capa de Aplicación dentro de la Arquitectura
 * Hexagonal del proyecto Builders-SAS. Su responsabilidad es desactivar una
 * relación usuario-rol estableciendo active=false y actualizando updatedAt.
 *
 * Reglas:
 * - La eliminación es lógica, nunca física.
 * - createdAt y assignedAt se preservan.
 * - updatedAt se establece al momento de la desactivación.
 * - Si el registro ya está inactivo o no existe, se devuelve Mono.empty().
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteUserRoleService implements DeleteUserRoleUseCase {

    private final UserRoleRepositoryPort port;

    @Override
    public Mono<Void> delete(Long id) {
        return port.findById(id)
                .filter(UserRoleRecord::active) // solo activos se pueden desactivar
                .switchIfEmpty(Mono.fromRunnable(() ->
                        log.warn("No se puede eliminar. Relación usuario-rol no activa o no existe. id={}", id)))
                .map(existing ->
                        new UserRoleRecord(
                                existing.id(),
                                existing.userId(),
                                existing.roleId(),
                                existing.assignedAt(),     // se preserva
                                existing.createdAt(),      // se preserva
                                LocalDateTime.now(),       // updatedAt
                                Boolean.FALSE              // soft delete
                        )
                )
                .flatMap(port::save)
                .doOnNext(r ->
                        log.info("Relación usuario-rol desactivada (soft delete). id={}", r.id()))
                .then(); // devuelve Mono<Void>
    }
}
