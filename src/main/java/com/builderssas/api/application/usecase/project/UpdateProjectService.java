package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.Project;
import com.builderssas.api.domain.port.in.project.UpdateProjectUseCase;
import com.builderssas.api.domain.port.out.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateProjectService implements UpdateProjectUseCase {

    private final ProjectRepository repository;

    @Override
    public Mono<Project> update(Long id, Project command) {
        Project updated = new Project(
            id,
            name, code, active
        );
        return repository.save(updated);
    }
}
