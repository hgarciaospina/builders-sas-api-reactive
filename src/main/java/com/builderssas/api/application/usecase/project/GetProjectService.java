package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.Project;
import com.builderssas.api.domain.port.in.project.GetProjectUseCase;
import com.builderssas.api.domain.port.out.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetProjectService implements GetProjectUseCase {

    private final ProjectRepository repository;

    @Override
    public Mono<Project> getById(Long id) {
        return repository.findById(id);
    }
}
