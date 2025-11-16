package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.UpdateProjectUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

    private final ProjectRepositoryPort repository;

    @Override
    public Mono<ProjectRecord> update(Long id, ProjectRecord command) {

        ProjectRecord updated = new ProjectRecord(
                id,
                command.name(),
                command.code(),
                command.active()
        );

        return repository.save(updated);
    }
}
