package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener una asignación específica usuario-rol.
 */
public interface FindUserRoleByUserIdAndRoleIdUseCase {
    Mono<UserRoleRecord> findByUserIdAndRoleId(Long userId, Long roleId);
}
