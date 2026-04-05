package com.builderssas.api.infrastructure.web.controller.auth;

import com.builderssas.api.domain.port.in.auth.*;
import com.builderssas.api.domain.model.auth.AuthSessionRecord;
import com.builderssas.api.domain.model.auth.RecoverPasswordRecord;

import com.builderssas.api.infrastructure.web.dto.auth.*;
import com.builderssas.api.infrastructure.web.mapper.AuthWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador de autenticación.
 *
 * Responsabilidad:
 * - Exponer endpoints HTTP
 * - Delegar en casos de uso
 * - Mapear dominio → DTO
 *
 * No contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final RecoverPasswordUseCase recoverPasswordUseCase;
    private final CreateAuthUserUseCase createAuthUserUseCase;

    /**
     * LOGIN
     */
    @PostMapping("/login")
    public Mono<AuthResultDto> login(@Valid @RequestBody LoginDto dto) {

        return loginUseCase.login(dto.username(), dto.password())
                .map(this::mapSessionToDto);
    }

    /**
     * REFRESH
     *
     * Solo devuelve nuevo ACCESS token
     */
    @PostMapping("/refresh")
    public Mono<AuthResultDto> refresh(@RequestHeader("Authorization") String authorization) {

        String refreshToken = authorization.replace("Bearer ", "");

        return refreshTokenUseCase.refresh(refreshToken)
                .map(this::mapRefreshToDto);
    }

    /**
     * LOGOUT
     */
    @PostMapping("/logout")
    public Mono<Void> logout(@RequestHeader("Authorization") String authorization) {

        String token = authorization.replace("Bearer ", "");
        return logoutUseCase.logout(token);
    }

    /**
     * CHANGE PASSWORD
     */
    @PostMapping("/change-password")
    public Mono<Void> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        return changePasswordUseCase.changePassword(dto);
    }

    /**
     * RECOVER PASSWORD
     */
    @PostMapping("/recover-password")
    public Mono<AuthResultDto> recoverPassword(@Valid @RequestBody RecoverPasswordDto dto) {

        return recoverPasswordUseCase
                .recover(dto.username())
                .map(this::mapRecoverToDto);
    }

    /**
     * CREATE CREDENTIALS
     */
    @PostMapping("/create-credentials")
    public Mono<Void> createCredentials(@Valid @RequestBody CreateAuthUserDto dto) {
        return createAuthUserUseCase.create(dto);
    }

    /**
     * Mapper LOGIN
     */
    private AuthResultDto mapSessionToDto(AuthSessionRecord session) {

        return AuthWebMapper.toAuthResultDto(
                session.user().userId(),
                session.user().username(),
                session.user().fullName(),
                session.user().roles(),
                session.accessToken(),
                session.refreshToken()
        );
    }

    /**
     * Mapper REFRESH
     */
    private AuthResultDto mapRefreshToDto(String accessToken) {

        return AuthWebMapper.toAuthResultDto(
                null,
                null,
                null,
                null,
                accessToken,
                null
        );
    }

    /**
     * Mapper RECOVER
     */
    private AuthResultDto mapRecoverToDto(RecoverPasswordRecord result) {

        return AuthWebMapper.toRecoverResultDto(
                result.userId(),
                result.username(),
                result.fullName(),
                result.role(),
                result.temporaryPassword()
        );
    }
}