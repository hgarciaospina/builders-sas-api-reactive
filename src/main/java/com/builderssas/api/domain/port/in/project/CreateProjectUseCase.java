package com.builderssas.api.domain.port.in.project;

import com.builderssas.api.domain.model.project.Project;
import reactor.core.publisher.Mono;

public interface CreateProjectUseCase {

    Mono<Project> create(Project command);
}
