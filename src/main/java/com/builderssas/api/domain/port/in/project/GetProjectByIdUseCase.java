package com.builderssas.api.domain.port.in.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import reactor.core.publisher.Mono;

public interface GetProjectByIdUseCase {
    Mono<ProjectRecord> getById(Long id);
}
