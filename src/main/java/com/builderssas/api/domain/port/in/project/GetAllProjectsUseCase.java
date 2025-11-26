package com.builderssas.api.domain.port.in.project;

import com.builderssas.api.domain.model.project.ProjectRecord;
import reactor.core.publisher.Flux;

public interface GetAllProjectsUseCase {
    Flux<ProjectRecord> findAll();
}
