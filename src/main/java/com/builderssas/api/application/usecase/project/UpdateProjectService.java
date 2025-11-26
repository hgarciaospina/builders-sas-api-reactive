package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.UpdateProjectUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public final class UpdateProjectService implements UpdateProjectUseCase {

    private final ProjectRepositoryPort repository;

    @Override
    public Mono<ProjectRecord> update(ProjectRecord updated) {

        return repository.findById(updated.id())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Proyecto no encontrado.")))

                // Validar si el nombre cambió
                .flatMap(existing ->
                        Mono.just(existing)
                                .filter(prev -> prev.name().equals(updated.name()))
                                // Nombre NO cambió → aceptar inmediatamente
                                .map(ignore -> updated)

                                // Nombre cambió → validar duplicado
                                .switchIfEmpty(
                                        repository.existsByName(updated.name())
                                                .filter(exists -> !exists)
                                                .switchIfEmpty(Mono.error(new DuplicateResourceException("Ya existe un proyecto con ese nombre.")))
                                                .map(ignore -> updated)
                                )
                )

                // Guardar
                .flatMap(repository::save);
    }
}
