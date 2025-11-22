package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todas las asignaciones usuario-rol,
 * incluyendo activas e inactivas.
 */
public interface GetAllUserRolesUseCase {
    Flux<UserRoleRecord> getAll();
}
