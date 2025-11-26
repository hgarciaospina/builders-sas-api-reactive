package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.CreateProjectUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public final class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort repository;

    @Override
    public Mono<ProjectRecord> create(ProjectRecord record) {

        return repository.existsByName(record.name())

                // nombre NO existe → permitir creación
                .filter(exists -> !exists)

                // nombre existe → error
                .switchIfEmpty(Mono.error(new DuplicateResourceException("Ya existe un proyecto con ese nombre.")))

                // guardar después de la validación funcional
                .flatMap(ignore -> repository.save(record));
    }
}
