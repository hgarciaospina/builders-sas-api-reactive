package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.Role;
import reactor.core.publisher.Mono;

public interface UpdateRoleUseCase {

    Mono<Role> update(Long id, Role command);
}
