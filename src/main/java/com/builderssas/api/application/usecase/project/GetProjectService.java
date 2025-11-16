package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.GetProjectUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetProjectService implements GetProjectUseCase {

    private final ProjectRepositoryPort repository;

    @Override
    public Mono<ProjectRecord> getById(Long id) {
        return repository.findById(id);
    }
}
