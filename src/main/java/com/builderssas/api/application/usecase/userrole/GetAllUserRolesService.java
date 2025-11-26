package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.GetAllUserRolesUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Servicio de aplicación para obtener todas las relaciones usuario-rol activas.
 *
 * Este servicio pertenece a la capa de Aplicación dentro de la Arquitectura
 * Hexagonal del proyecto Builders-SAS. Su responsabilidad es recuperar
 * todas las asignaciones usuario-rol vigentes (active = true).
 *
 * Reglas:
 * - Solo se devuelven registros activos.
 * - No se mezclan registros inactivos ni eliminados lógicamente.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllUserRolesService implements GetAllUserRolesUseCase {

    private final UserRoleRepositoryPort port;

    @Override
    public Flux<UserRoleRecord> getAll() {
        return port.findAll()
                .filter(UserRoleRecord::active)
                .doOnSubscribe(s ->
                        log.info("Consultando todas las relaciones usuario-rol activas"))
                .doOnNext(r ->
                        log.debug("Relacion usuario-rol activa: id={}, userId={}, roleId={}",
                                r.id(), r.userId(), r.roleId()));
    }
}
