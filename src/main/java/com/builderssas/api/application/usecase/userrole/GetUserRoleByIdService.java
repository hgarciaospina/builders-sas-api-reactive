package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.GetUserRoleByIdUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUserRoleByIdService implements GetUserRoleByIdUseCase {

    private final UserRoleRepositoryPort repository;

    @Override
    public Mono<UserRoleRecord> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("No existe un UserRole con id: " + id)
                ))
                .doOnNext(r -> log.info("UserRole id={} obtenido correctamente.", id));
    }
}
