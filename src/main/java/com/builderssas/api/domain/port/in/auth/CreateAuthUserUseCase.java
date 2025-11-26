package com.builderssas.api.domain.port.in.auth;

import com.builderssas.api.infrastructure.web.dto.auth.CreateAuthUserDto;
import reactor.core.publisher.Mono;

public interface CreateAuthUserUseCase {
    Mono<Void> create(CreateAuthUserDto dto);
}