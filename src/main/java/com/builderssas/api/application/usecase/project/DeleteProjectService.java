package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.port.in.project.DeleteProjectUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para aplicar borrado lógico en proyectos.
 *
 * Reglas:
 * - No existe delete físico.
 * - Se marca active = false mediante softDelete() desde el repositorio.
 * - Si el proyecto no existe → error.
 *
 * Cumple arquitectura:
 * - 100% funcional.
 * - Sin lógico imperativa.
 * - Sin if, sin ternaria.
 * - Sin recrear records gigantes.
 * - Sin contaminar ProjectRecord.
 */
@Service
@RequiredArgsConstructor
public final class DeleteProjectService implements DeleteProjectUseCase {

    private final ProjectRepositoryPort repository;

    @Override
    public Mono<Void> delete(Long id) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Proyecto no encontrado.")))
                .flatMap(p -> repository.softDelete(id))
                .then();
    }
}
