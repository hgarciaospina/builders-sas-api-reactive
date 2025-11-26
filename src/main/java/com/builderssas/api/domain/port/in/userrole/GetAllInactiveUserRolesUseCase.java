package com.builderssas.api.domain.port.in.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import reactor.core.publisher.Flux;

public interface GetAllInactiveUserRolesUseCase {
    Flux<UserRoleRecord> getAllInactive();
}
