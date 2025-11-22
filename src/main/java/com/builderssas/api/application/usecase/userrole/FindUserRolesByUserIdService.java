package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.FindUserRolesByUserIdUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Caso de uso para obtener todas las asignaciones usuario-rol
 * pertenecientes a un usuario específico.
 *
 * Este servicio forma parte de la capa de Aplicación dentro de la
 * Arquitectura Hexagonal.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FindUserRolesByUserIdService implements FindUserRolesByUserIdUseCase {

    private final UserRoleRepositoryPort port;

    @Override
    public Flux<UserRoleRecord> findByUserId(Long userId) {
        return port.findByUserId(userId)
                .doOnSubscribe(s ->
                        log.info("Buscando roles para userId={}", userId))
                .doOnNext(r ->
                        log.info("Rol encontrado: roleId={} para userId={}", r.roleId(), r.userId()));
    }
}
