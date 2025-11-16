package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.AssignUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementación del caso de uso para asignar un rol a un usuario.
 *
 * Esta clase pertenece a la capa de Aplicación dentro de la Arquitectura Hexagonal.
 * Su responsabilidad es orquestar la creación de una nueva relación usuario-rol,
 * garantizando la correcta inicialización de los campos de auditoría y de estado.
 *
 * Decisiones de diseño:
 * - Los campos de auditoría (assignedAt, createdAt, updatedAt) se generan
 *   en este caso de uso, utilizando la hora actual del sistema.
 * - El campo active se establece en true al momento de la creación.
 * - El id se deja en null para que la capa de persistencia lo genere.
 *
 * Entradas esperadas:
 * - Se utiliza userId y roleId del record recibido.
 * - Los demás campos del record de entrada se ignoran en favor de los valores
 *   calculados en este caso de uso.
 */
@RequiredArgsConstructor
@Slf4j
public class AssignUserRoleUseCaseService implements AssignUserRoleUseCase {

    /**
     * Puerto de salida responsable de las operaciones de persistencia
     * para las asignaciones usuario-rol.
     */
    private final UserRoleRepositoryPort repository;

    /**
     * Asigna un rol a un usuario creando una nueva relación usuario-rol.
     *
     * @param input record de dominio con los datos mínimos de la asignación
     *              (principalmente userId y roleId).
     * @return Mono con el registro creado y persistido.
     */
    @Override
    public Mono<UserRoleRecord> assign(UserRoleRecord input) {
        return Mono.fromSupplier(LocalDateTime::now)
                .map(now ->
                        new UserRoleRecord(
                                null,                  // id generado por la persistencia
                                input.userId(),        // usuario al que se asigna el rol
                                input.roleId(),        // rol asignado
                                now,                   // assignedAt
                                now,                   // createdAt
                                now,                   // updatedAt
                                Boolean.TRUE           // active
                        )
                )
                .doOnNext(r ->
                        log.info("Creando asignación usuario-rol: userId={}, roleId={}", r.userId(), r.roleId()))
                .flatMap(repository::save)
                .doOnNext(saved ->
                        log.info("Asignación usuario-rol creada con id {}", saved.id()));
    }
}
