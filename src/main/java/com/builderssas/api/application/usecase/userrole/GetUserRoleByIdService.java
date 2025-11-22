package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.GetUserRoleByIdUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio de aplicación para obtener una relación usuario-rol
 * a partir de su identificador.
 *
 * Este servicio pertenece a la capa de Aplicación dentro de la
 * Arquitectura Hexagonal del proyecto Builders-SAS. Su responsabilidad
 * es recuperar una asignación usuario-rol existente filtrando
 * únicamente registros activos.
 *
 * Reglas:
 * - Solo se consideran registros con active = true.
 * - Si el registro no existe o está inactivo, se devuelve Mono.empty().
 * - No se producen excepciones en este servicio; las excepciones
 *   quedan a cargo de los handlers globales si se requieren.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetUserRoleByIdService implements GetUserRoleByIdUseCase {

    private final UserRoleRepositoryPort port;

    @Override
    public Mono<UserRoleRecord> getById(Long id) {
        return port.findById(id)
                .filter(UserRoleRecord::active)
                .doOnNext(r ->
                        log.info("Relación usuario-rol encontrada con id {}", r.id()))
                .switchIfEmpty(
                        Mono.fromRunnable(() ->
                                log.warn("No se encontró relación usuario-rol activa con id {}", id)
                        )
                );
    }
}
