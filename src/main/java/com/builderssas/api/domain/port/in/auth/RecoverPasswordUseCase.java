package com.builderssas.api.domain.port.in.auth;

import com.builderssas.api.infrastructure.web.dto.auth.RecoverPasswordDto;
import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;
import reactor.core.publisher.Mono;

public interface RecoverPasswordUseCase {

    Mono<AuthResultDto> recover(RecoverPasswordDto dto);
}
