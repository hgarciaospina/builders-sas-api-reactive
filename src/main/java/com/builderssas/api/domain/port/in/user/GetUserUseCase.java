package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.User;
import reactor.core.publisher.Mono;

public interface GetUserUseCase {

    Mono<User> getById(Long id);
}
