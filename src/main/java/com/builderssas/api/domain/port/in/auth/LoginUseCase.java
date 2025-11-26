package com.builderssas.api.domain.port.in.auth;

import com.builderssas.api.infrastructure.web.dto.auth.LoginDto;
import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;
import reactor.core.publisher.Mono;

public interface LoginUseCase {

    Mono<AuthResultDto> login(LoginDto dto);
}
