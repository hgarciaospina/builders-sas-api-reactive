package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.GetAllInactiveUserRolesUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todas las relaciones usuario-rol inactivas.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllInactiveUserRolesService implements GetAllInactiveUserRolesUseCase {

    private final UserRoleRepositoryPort repository;

    @Override
    public Flux<UserRoleRecord> getAllInactive() {
        return repository.findAllInactive()
                .switchIfEmpty(Flux.error(new GlobalExceptionHandler.ResourceNotFoundException("No existen UserRoles inactivos.")))
                .doOnSubscribe(s -> log.info("Consultando UserRoles inactivos…"))
                .doOnComplete(() -> log.info("Consulta de UserRoles inactivos finalizada."));
    }
}
