package com.builderssas.api.domain.port.in.project;

import com.builderssas.api.domain.model.project.Project;
import reactor.core.publisher.Flux;

public interface ListProjectsUseCase {

    Flux<Project> listAll();
}
