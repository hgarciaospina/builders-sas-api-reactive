package com.builderssas.api.application.usecase.auth;

import com.builderssas.api.domain.port.in.auth.CreateAuthUserUseCase;
import com.builderssas.api.domain.port.out.auth.AuthUserRepositoryPort;

import com.builderssas.api.infrastructure.web.dto.auth.CreateAuthUserDto;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import com.builderssas.api.infrastructure.persistence.repository.UserR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;

@Service
@RequiredArgsConstructor
public class CreateAuthUserService implements CreateAuthUserUseCase {

    private final UserR2dbcRepository userRepository;
    private final AuthUserRepositoryPort authRepository;

    @Override
    public Mono<Void> create(CreateAuthUserDto dto) {

        return userRepository.findById(dto.userId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuario no encontrado")))

                .flatMap(user -> {

                    String hashed = BCrypt.hashpw(dto.password(), BCrypt.gensalt());

                    return Mono.just(
                            new com.builderssas.api.domain.model.auth.AuthUserRecord(
                                    null,
                                    user.getId(),
                                    hashed,
                                    true,
                                    LocalDateTime.now(),
                                    LocalDateTime.now()
                            )
                    );
                })
                .flatMap(authRepository::save)
                .then();
    }
}
