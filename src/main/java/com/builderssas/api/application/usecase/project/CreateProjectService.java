package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.CreateProjectUseCase;
import com.builderssas.api.domain.port.out.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepository repository;

    @Override
    public Mono<ProjectRecord> create(ProjectRecord command) {
        return repository.save(command);
    }
}
