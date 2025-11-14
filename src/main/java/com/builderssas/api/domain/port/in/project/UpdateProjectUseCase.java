package com.builderssas.api.domain.port.in.project;

import com.builderssas.api.domain.model.project.Project;
import reactor.core.publisher.Mono;

public interface UpdateProjectUseCase {

    Mono<Project> update(Long id, Project command);
}
