package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener solo las asignaciones usuario-rol activas.
 */
public interface GetAllActiveUserRolesUseCase {
    Flux<UserRoleRecord> getAllActive();
}
