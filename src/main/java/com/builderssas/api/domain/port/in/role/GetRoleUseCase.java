package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Mono;

public interface GetRoleUseCase {

    Mono<RoleRecord> getById(Long id);
}
