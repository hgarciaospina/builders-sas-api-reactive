package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para crear una asignación usuario-rol.
 */
public interface CreateUserRoleUseCase {
    Mono<UserRoleRecord> create(UserRoleRecord record);
}
