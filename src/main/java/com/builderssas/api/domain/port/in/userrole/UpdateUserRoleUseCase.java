package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para actualizar una relación usuario-rol existente.
 */
public interface UpdateUserRoleUseCase {
    Mono<UserRoleRecord> update(Long id, UserRoleRecord record);
}
