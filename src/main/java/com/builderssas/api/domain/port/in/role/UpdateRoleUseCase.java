package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Mono;

public interface UpdateRoleUseCase {
    Mono<RoleRecord> update(Long id, RoleRecord role);
}
