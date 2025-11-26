package com.builderssas.api.domain.port.in.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import reactor.core.publisher.Flux;

public interface ListRolesUseCase {
    Flux<RoleRecord> listAll();
}
