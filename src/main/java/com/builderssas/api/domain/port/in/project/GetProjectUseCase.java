package com.builderssas.api.domain.port.in.project;

import com.builderssas.api.domain.model.project.Project;
import reactor.core.publisher.Mono;

public interface GetProjectUseCase {

    Mono<Project> getById(Long id);
}
