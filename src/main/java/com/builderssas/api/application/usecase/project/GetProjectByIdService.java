package com.builderssas.api.application.usecase.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.GetProjectByIdUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.infrastructure.web.handler.GlobalExceptionHandler.ResourceNotFoundException;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class GetProjectByIdService implements GetProjectByIdUseCase {

    private final ProjectRepositoryPort projectRepositoryPort;

    @Override
    public Mono<ProjectRecord> getById(Long id) {
        return projectRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Proyecto no encontrado.")));
    }
}
