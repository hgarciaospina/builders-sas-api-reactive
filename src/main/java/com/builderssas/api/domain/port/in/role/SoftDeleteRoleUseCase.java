package com.builderssas.api.domain.port.in.role;

import reactor.core.publisher.Mono;
import com.builderssas.api.domain.model.role.RoleRecord;

public interface SoftDeleteRoleUseCase {
    Mono<RoleRecord> softDelete(Long id);
}
