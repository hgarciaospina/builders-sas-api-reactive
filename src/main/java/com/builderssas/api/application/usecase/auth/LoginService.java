package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.port.in.auth.LoginUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;

import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;

import com.builderssas.api.infrastructure.web.dto.auth.LoginDto;
import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.UnauthorizedException;

import com.builderssas.api.infrastructure.web.mapper.AuthWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.mindrot.jbcrypt.BCrypt;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final AuthUserRepositoryPort authRepository;
    private final UserR2dbcRepository userRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;

    @Override
    public Mono<AuthResultDto> login(LoginDto dto) {

        return userRepository.findByUsername(dto.username())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuario no encontrado")))

                .flatMap(user ->
                        authRepository.findByUserId(user.getId())
                                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credenciales no encontradas")))

                                // ✔ Comparación correcta usando BCrypt
                                .filter(auth -> BCrypt.checkpw(dto.password(), auth.passwordHash()))
                                .switchIfEmpty(Mono.error(new UnauthorizedException("Contraseña incorrecta")))

                                .flatMap(auth ->
                                        userRoleRepository.findByUserId(user.getId())
                                                .next()
                                                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Rol no asignado")))

                                                .flatMap(userRole ->
                                                        roleRepository.findById(userRole.getRoleId())
                                                                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Rol no encontrado")))
                                                                .map(role ->
                                                                        AuthWebMapper.toAuthResultDto(
                                                                                user.getId(),
                                                                                user.getUsername(),
                                                                                user.getDisplayName(),
                                                                                role.getName()
                                                                        )
                                                                )
                                                )
                                )
                );
    }
}
