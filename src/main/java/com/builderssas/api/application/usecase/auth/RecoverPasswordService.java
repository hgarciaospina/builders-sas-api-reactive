package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.port.in.auth.RecoverPasswordUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;

import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.UserRoleR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;

import com.builderssas.api.infrastructure.web.dto.auth.RecoverPasswordDto;
import com.builderssas.api.infrastructure.web.dto.auth.AuthResultDto;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

@Service
@RequiredArgsConstructor
public class RecoverPasswordService implements RecoverPasswordUseCase {

    private final UserR2dbcRepository userRepository;
    private final AuthUserRepositoryPort authRepository;
    private final UserRoleR2dbcRepository userRoleRepository;
    private final RoleR2dbcRepository roleRepository;

    @Override
    public Mono<AuthResultDto> recover(RecoverPasswordDto dto) {

        return userRepository.findByUsername(dto.username())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuario no encontrado")))

                .flatMap(user ->
                        authRepository.findByUserId(user.getId())
                                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credenciales no encontradas")))

                                .flatMap(auth -> {

                                    var tempPassword = generateTempPassword();
                                    var hashed = BCrypt.hashpw(tempPassword, BCrypt.gensalt());

                                    var updated = new com.builderssas.api.domain.model.auth.AuthUserRecord(
                                            auth.id(),
                                            auth.userId(),
                                            hashed,
                                            auth.active(),
                                            auth.createdAt(),
                                            LocalDateTime.now()
                                    );

                                    return authRepository.save(updated)
                                            .then(
                                                    userRoleRepository.findByUserId(user.getId()).next()
                                            )
                                            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Rol no asignado")))
                                            .flatMap(userRole ->
                                                    roleRepository.findById(userRole.getRoleId())
                                                            .switchIfEmpty(Mono.error(new ResourceNotFoundException("Rol no encontrado")))
                                                            .map(role ->
                                                                    new AuthResultDto(
                                                                            user.getId(),
                                                                            user.getUsername(),
                                                                            user.getDisplayName(),
                                                                            role.getName(),
                                                                            true,
                                                                            tempPassword   // ← devuelto correctamente
                                                                    )
                                                            )
                                            );
                                })
                );
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}