package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.User;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {

    Mono<User> create(User command);
}
