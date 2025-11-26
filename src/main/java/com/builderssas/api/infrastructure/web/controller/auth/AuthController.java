package com.builderssas.api.infrastructure.web.controller.auth;

import com.builderssas.api.domain.port.in.auth.LoginUseCase;
import com.builderssas.api.domain.port.in.auth.ChangePasswordUseCase;
import com.builderssas.api.domain.port.in.auth.RecoverPasswordUseCase;
import com.builderssas.api.domain.port.in.auth.CreateAuthUserUseCase;

import com.builderssas.api.infrastructure.web.dto.auth.LoginDto;
import com.builderssas.api.infrastructure.web.dto.auth.ChangePasswordDto;
import com.builderssas.api.infrastructure.web.dto.auth.RecoverPasswordDto;
import com.builderssas.api.infrastructure.web.dto.auth.CreateAuthUserDto;
import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final RecoverPasswordUseCase recoverPasswordUseCase;
    private final CreateAuthUserUseCase createAuthUserUseCase; // ← SOLO AÑADO ESTO

    @PostMapping("/login")
    public Mono<AuthResultDto> login(@Valid @RequestBody LoginDto dto) {
        return loginUseCase.login(dto);
    }

    @PostMapping("/change-password")
    public Mono<Void> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        return changePasswordUseCase.changePassword(dto);
    }

    @PostMapping("/recover-password")
    public Mono<AuthResultDto> recoverPassword(@Valid @RequestBody RecoverPasswordDto dto) {
        return recoverPasswordUseCase.recover(dto);
    }
    @PostMapping("/create-credentials")
    public Mono<Void> createCredentials(@Valid @RequestBody CreateAuthUserDto dto) {
        return createAuthUserUseCase.create(dto);
    }
}