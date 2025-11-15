package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.UserRecord;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {

    Mono<UserRecord> update(Long id, UserRecord command);
}
