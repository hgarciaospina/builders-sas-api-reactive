package com.builderssas.api.domain.port.in.auth;

import com.builderssas.api.infrastructure.web.dto.auth.ChangePasswordDto;
import reactor.core.publisher.Mono;

public interface ChangePasswordUseCase {

    Mono<Void> changePassword(ChangePasswordDto dto);
}
