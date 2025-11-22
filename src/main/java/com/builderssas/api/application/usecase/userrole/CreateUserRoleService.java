package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.CreateUserRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación responsable de crear una nueva relación usuario-rol.
 *
 * Este servicio forma parte de la capa de Aplicación dentro de la Arquitectura
 * Hexagonal del proyecto Builders-SAS. Su responsabilidad es orquestar la
 * creación de una asignación usuario-rol inicializando correctamente los
 * campos de auditoría y estado.
 *
 * Decisiones de diseño:
 * - assignedAt, createdAt y updatedAt se generan aquí usando LocalDateTime.now().
 * - active se establece en true para indicar una relación vigente.
 * - El id se deja nulo para que la capa de persistencia lo genere automáticamente.
 *
 * Entradas esperadas:
 * - userId y roleId provenientes del record de entrada.
 * - Los demás campos enviados por el cliente son ignorados, ya que los valores
 *   definitivos se establecen aquí.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserRoleService implements CreateUserRoleUseCase {

    /**
     * Puerto de salida utilizado para persistir la nueva relación usuario-rol.
     */
    private final UserRoleRepositoryPort port;

    /**
     * Crea una nueva asignación usuario-rol.
     *
     * @param input record con userId y roleId.
     * @return Mono con el registro creado.
     */
    @Override
    public Mono<UserRoleRecord> create(UserRoleRecord input) {
        return Mono.fromSupplier(LocalDateTime::now)
                .map(now ->
                        new UserRoleRecord(
                                null,            // id generado por la persistencia
                                input.userId(),  // usuario al que se asigna el rol
                                input.roleId(),  // rol asignado
                                now,             // assignedAt
                                now,             // createdAt
                                now,             // updatedAt
                                Boolean.TRUE     // active
                        )
                )
                .doOnNext(r ->
                        log.info("Creando asignación usuario-rol: userId={}, roleId={}", r.userId(), r.roleId()))
                .flatMap(port::save)
                .doOnNext(saved ->
                        log.info("Asignación usuario-rol creada con id {}", saved.id()));
    }
}
