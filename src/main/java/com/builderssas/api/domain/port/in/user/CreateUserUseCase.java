package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {

    Mono<UserRecord> create(UserRecord command);
}
