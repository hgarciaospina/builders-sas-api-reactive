package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Mono;

public interface GetRoleByIdUseCase {
    Mono<RoleRecord> findById(Long id);
}
