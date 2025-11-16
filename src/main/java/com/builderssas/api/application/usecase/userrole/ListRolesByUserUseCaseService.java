package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.ListRolesByUserUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para obtener todos los roles asignados
 * a un usuario específico.
 *
 * Esta clase pertenece a la capa de Aplicación dentro de la Arquitectura Hexagonal.
 * Su responsabilidad es orquestar el flujo de lectura a través del puerto de salida,
 * respetando la inmutabilidad del dominio y la consistencia empresarial.
 *
 * Decisiones de diseño:
 * - No se modifica ningún campo temporal (assignedAt, createdAt, updatedAt).
 * - No se altera el valor de active.
 * - Este caso de uso únicamente delega la consulta al puerto OUT.
 */
@RequiredArgsConstructor
@Slf4j
public class ListRolesByUserUseCaseService implements ListRolesByUserUseCase {

    /**
     * Puerto de salida responsable de recuperar las asignaciones usuario-rol.
     */
    private final UserRoleRepositoryPort repository;

    /**
     * Lista todos los roles asignados a un usuario.
     *
     * @param userId identificador del usuario.
     * @return Flux con todas las asignaciones correspondientes al usuario.
     */
    @Override
    public Flux<UserRoleRecord> listByUser(Long userId) {
        return repository.findByUserId(userId)
                .doOnSubscribe(s ->
                        log.info("Buscando roles asignados al usuario {}", userId))
                .doOnComplete(() ->
                        log.info("Finaliza la búsqueda de roles para el usuario {}", userId));
    }
}
