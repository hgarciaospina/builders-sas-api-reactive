package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener una relación usuario-rol por su ID.
 */
public interface GetUserRoleByIdUseCase {
    Mono<UserRoleRecord> getById(Long id);
}
