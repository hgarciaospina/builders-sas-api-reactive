package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.Role;
import reactor.core.publisher.Mono;

public interface GetRoleUseCase {

    Mono<Role> getById(Long id);
}
