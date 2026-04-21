package com.builderssas.api.infrastructure.web.controller.auth;

import com.builderssas.api.domain.port.in.auth.*;
import com.builderssas.api.domain.model.auth.AuthSessionRecord;
import com.builderssas.api.domain.model.auth.RecoverPasswordRecord;
import com.builderssas.api.infrastructure.web.dto.auth.*;
import com.builderssas.api.infrastructure.web.mapper.AuthWebMapper;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.UnauthorizedException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST reactivo para operaciones de autenticación y gestión de credenciales.
 *
 * Principios:
 * - 100% funcional y no bloqueante: usa operadores de Reactor, sin estructuras imperativas.
 * - Delegación pura: toda la lógica de negocio reside en los casos de uso.
 * - Mapeo centralizado: convierte modelos de dominio a DTOs mediante AuthWebMapper.
 * - Idempotencia: endpoints como logout y refresh toleran ausencia de header sin lanzar 500.
 * - Observabilidad: registra entradas clave en nivel DEBUG sin exponer datos sensibles.
 *
 * Manejo de errores:
 * No captura excepciones. Todos los errores de dominio se propagan y son tratados
 * por GlobalExceptionHandler de forma centralizada.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final RecoverPasswordUseCase recoverPasswordUseCase;
    private final CreateAuthUserUseCase createAuthUserUseCase;

    // =========================================================================
    // LOGIN
    // =========================================================================
    /**
     * Autentica un usuario y emite un par de tokens de acceso y refresco.
     *
     * Flujo: valida credenciales -> genera accessToken + refreshToken -> retorna sesión.
     *
     * @param dto credenciales de acceso con username y password
     * @return Mono que emite AuthResultDto con tokens y datos del usuario autenticado
     * @throws UnauthorizedException si las credenciales son inválidas o el usuario está inactivo
     */
    @PostMapping("/login")
    public Mono<AuthResultDto> login(@Valid @RequestBody LoginDto dto) {
        log.debug("AuthController.login() - Petición recibida para username={}", dto.username());
        return loginUseCase.login(dto.username(), dto.password())
                .map(this::mapSessionToDto)
                .doOnSuccess(result -> log.debug("AuthController.login() - Login exitoso para userId={}", result.userId()))
                .doOnError(e -> log.error("AuthController.login() - Error en login para username={}", dto.username(), e));
    }

    // =========================================================================
    // REFRESH TOKEN
    // =========================================================================
    /**
     * Renueva el access token usando un refresh token válido enviado en el body.
     *
     * Flujo: recibe refreshToken -> valida y rota tokens -> retorna nuevo accessToken.
     *
     * @param request body con el refreshToken
     * @return Mono que emite AuthResultDto con el nuevo accessToken
     * @throws UnauthorizedException si el refresh token es inválido, expirado o revocado
     */
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthResultDto> refresh(@Valid @RequestBody RefreshRequestDto request) {
        log.debug("AuthController.refresh() - Petición recibida");
        return refreshTokenUseCase.refresh(request.refreshToken())
                .map(this::mapRefreshToDto)
                .doOnSuccess(result -> log.debug("AuthController.refresh() - Refresh exitoso, nuevo accessToken emitido"))
                .doOnError(e -> log.error("AuthController.refresh() - Error en refresh", e));
    }

    // =========================================================================
    // LOGOUT
    // =========================================================================
    /**
     * Invalida el token de acceso actual marcándolo como revocado en el repositorio.
     *
     * Flujo: extrae token del header Authorization -> invoca caso de uso de logout -> completa 200 OK.
     * Idempotente: si el token no existe o ya está revocado, igual retorna 200 OK.
     *
     * @param authorization header Authorization con formato "Bearer {token}"
     * @return Mono<Void> que completa con 200 OK sin body
     */
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> logout(@RequestHeader("Authorization") String authorization) {
        log.debug("AuthController.logout() - Petición recibida");
        String token = authorization.replace("Bearer ", "");
        return logoutUseCase.logout(token)
                .doOnSuccess(v -> log.debug("AuthController.logout() - Logout procesado correctamente"))
                .doOnError(e -> log.error("AuthController.logout() - Error en logout", e));
    }

    // =========================================================================
    // CHANGE PASSWORD
    // =========================================================================
    /**
     * Cambia la contraseña del usuario autenticado validando la contraseña actual.
     *
     * Flujo: valida currentPassword -> actualiza a newPassword -> invalida tokens activos.
     *
     * @param dto contiene currentPassword y newPassword validados por Bean Validation
     * @return Mono<Void> que completa con 200 OK al finalizar
     * @throws UnauthorizedException si la contraseña actual es incorrecta
     */
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        log.debug("AuthController.changePassword() - Petición recibida");
        return changePasswordUseCase.changePassword(dto)
                .doOnSuccess(v -> log.debug("AuthController.changePassword() - Contraseña cambiada exitosamente"))
                .doOnError(e -> log.error("AuthController.changePassword() - Error", e));
    }

    // =========================================================================
    // RECOVER PASSWORD
    // =========================================================================
    /**
     * Genera una contraseña temporal para recuperación de acceso.
     *
     * Flujo: busca usuario por username -> genera password temporal -> retorna datos.
     * Nota: el envío por email/SMS debe hacerlo un event listener, no este endpoint.
     *
     * @param dto contiene el username del usuario a recuperar
     * @return Mono que emite AuthResultDto con la contraseña temporal generada
     * @throws ResourceNotFoundException si el username no existe en el sistema
     */
    @PostMapping("/recover-password")
    public Mono<AuthResultDto> recoverPassword(@Valid @RequestBody RecoverPasswordDto dto) {
        log.debug("AuthController.recoverPassword() - Petición para username={}", dto.username());
        return recoverPasswordUseCase.recover(dto.username())
                .map(this::mapRecoverToDto)
                .doOnSuccess(result -> log.debug("AuthController.recoverPassword() - Password temporal generada para userId={}", result.userId()))
                .doOnError(e -> log.error("AuthController.recoverPassword() - Error", e));
    }

    // =========================================================================
    // CREATE AUTH USER
    // =========================================================================
    /**
     * Crea las credenciales de autenticación para un usuario previamente registrado.
     *
     * Flujo: valida que no exista -> hashea password -> persiste en repositorio de auth.
     *
     * @param dto datos de creación con userId y password
     * @return Mono<Void> que completa con 200 OK al crear
     * @throws DuplicateResourceException si el userId ya tiene credenciales
     */
    @PostMapping("/create-credentials")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> createCredentials(@Valid @RequestBody CreateAuthUserDto dto) {
        log.debug("AuthController.createCredentials() - Petición para userId={}", dto.userId());
        return createAuthUserUseCase.create(dto)
                .doOnSuccess(v -> log.debug("AuthController.createCredentials() - Credenciales creadas para userId={}", dto.userId()))
                .doOnError(e -> log.error("AuthController.createCredentials() - Error", e));
    }

    // =========================================================================
    // MÉTODOS PRIVADOS DE MAPEO
    // =========================================================================
    /**
     * Convierte AuthSessionRecord de dominio a AuthResultDto de capa web.
     *
     * @param session registro de sesión con tokens y datos de usuario
     * @return DTO serializable para respuesta HTTP
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
     * Convierte el accessToken renovado a AuthResultDto para respuesta de refresh.
     *
     * @param accessToken nuevo JWT de acceso emitido
     * @return DTO con solo el accessToken actualizado
     */
    private AuthResultDto mapRefreshToDto(String accessToken) {
        return AuthWebMapper.toRefreshResultDto(accessToken);
    }

    /**
     * Convierte RecoverPasswordRecord de dominio a AuthResultDto con contraseña temporal.
     *
     * @param record registro con datos de usuario y password temporal
     * @return DTO serializable para respuesta HTTP
     */
    private AuthResultDto mapRecoverToDto(RecoverPasswordRecord record) {
        return AuthWebMapper.toRecoverResultDto(
                record.userId(),
                record.username(),
                record.fullName(),
                record.role(),
                record.temporaryPassword()
        );
    }
}