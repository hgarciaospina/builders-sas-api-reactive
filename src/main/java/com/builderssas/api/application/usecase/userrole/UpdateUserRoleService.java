package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.UpdateUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para actualizar una relación usuario-rol existente.
 *
 * Este servicio forma parte de la capa de Aplicación dentro de la Arquitectura
 * Hexagonal del proyecto Builders-SAS. Su responsabilidad es actualizar los
 * campos permitidos manteniendo la inmutabilidad histórica:
 *
 * Reglas:
 * - Solo se actualizan: userId, roleId y updatedAt.
 * - assignedAt y createdAt se mantienen sin cambios.
 * - active no se modifica aquí (solo en soft delete).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserRoleService implements UpdateUserRoleUseCase {

    private final UserRoleRepositoryPort port;

    @Override
    public Mono<UserRoleRecord> update(Long id, UserRoleRecord input) {
        return port.findById(id)
                .filter(UserRoleRecord::active) // solo activos
                .switchIfEmpty(Mono.fromRunnable(() ->
                        log.warn("No se encontró relación usuario-rol activa para actualizar. id={}", id)))
                .map(existing ->
                        new UserRoleRecord(
                                existing.id(),             // mismo ID
                                input.userId(),            // nuevo userId
                                input.roleId(),            // nuevo roleId
                                existing.assignedAt(),     // no cambia
                                existing.createdAt(),      // no cambia
                                LocalDateTime.now(),       // updatedAt
                                existing.active()          // se conserva
                        )
                )
                .flatMap(port::save)
                .doOnNext(updated ->
                        log.info("Relación usuario-rol actualizada: id={}, userId={}, roleId={}",
                                updated.id(), updated.userId(), updated.roleId()));
    }
}
