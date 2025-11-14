package com.builderssas.api.domain.port.in.user;

import com.builderssas.api.domain.model.user.User;
import reactor.core.publisher.Flux;

public interface ListUsersUseCase {

    Flux<User> listAll();
}
