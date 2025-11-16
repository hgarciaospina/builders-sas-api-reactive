package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.ListProjectsUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListProjectsService implements ListProjectsUseCase {

    private final ProjectRepositoryPort repository;

    @Override
    public Flux<ProjectRecord> listAll() {
        return repository.findAll();
    }
}
