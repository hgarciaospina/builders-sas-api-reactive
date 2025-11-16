package com.builderssas.api.application.usecase.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.ListUsersByRoleUseCase;
import com.builderssas.api.domain.port.out.userrole.UserRoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Implementación del caso de uso para obtener todos los usuarios
 * asociados a un rol específico.
 *
 * Esta clase pertenece a la capa de Aplicación dentro de la Arquitectura Hexagonal.
 * Su responsabilidad es orquestar la lectura de asignaciones usuario-rol
 * apoyándose en el puerto de salida sin modificar el modelo de dominio.
 *
 * Decisiones de diseño:
 * - No se modifican los campos assignedAt, createdAt, updatedAt ni active.
 * - La operación es puramente de consulta, sin efectos secundarios en el estado.
 */
@RequiredArgsConstructor
@Slf4j
public class ListUsersByRoleUseCaseService implements ListUsersByRoleUseCase {

    /**
     * Puerto de salida responsable de recuperar las asignaciones
     * usuario-rol desde la infraestructura.
     */
    private final UserRoleRepositoryPort repository;

    /**
     * Lista todas las asignaciones en las que participa el rol indicado.
     *
     * @param roleId identificador del rol.
     * @return Flux con las asignaciones usuario-rol correspondientes.
     */
    @Override
    public Flux<UserRoleRecord> listByRole(Long roleId) {
        return repository.findByRoleId(roleId)
                .doOnSubscribe(s ->
                        log.info("Buscando usuarios asociados al rol {}", roleId))
                .doOnComplete(() ->
                        log.info("Finaliza la búsqueda de usuarios para el rol {}", roleId));
    }
}
