package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.GetAllActiveUserRolesUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Servicio para listar todas las relaciones usuario-rol activas.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllActiveUserRolesService implements GetAllActiveUserRolesUseCase {

    private final UserRoleRepositoryPort repository;

    @Override
    public Flux<UserRoleRecord> getAllActive() {
        return repository.findAllActive()
                .doOnSubscribe(s -> log.info("Listando UserRoles activos"))
                .doOnNext(r -> log.debug("Activo → {}", r));
    }
}
