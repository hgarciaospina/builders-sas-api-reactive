// GetAllProjectsService.java
package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.GetAllProjectsUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class GetAllProjectsService implements GetAllProjectsUseCase {

    private final ProjectRepositoryPort projectRepositoryPort;

    @Override
    public Flux<ProjectRecord> findAll() {
        return projectRepositoryPort.findAll();
    }
}
