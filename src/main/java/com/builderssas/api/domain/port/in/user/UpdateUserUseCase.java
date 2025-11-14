package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.User;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {

    Mono<User> update(Long id, User command);
}
