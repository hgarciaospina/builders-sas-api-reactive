package com.builderssas.api.domain.port.in.role;

import reactor.core.publisher.Mono;
import com.builderssas.api.domain.model.role.RoleRecord;

public interface ToggleRoleStatusUseCase {
    Mono<RoleRecord> toggleStatus(Long id);
}
