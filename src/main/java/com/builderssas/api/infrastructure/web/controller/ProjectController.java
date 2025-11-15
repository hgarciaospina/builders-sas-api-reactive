package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.in.project.CreateProjectUseCase;
import com.builderssas.api.domain.port.in.project.UpdateProjectUseCase;
import com.builderssas.api.domain.port.in.project.GetProjectUseCase;
import com.builderssas.api.domain.port.in.project.ListProjectsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final CreateProjectUseCase createUseCase;
    private final UpdateProjectUseCase updateUseCase;
    private final GetProjectUseCase getUseCase;
    private final ListProjectsUseCase listUseCase;

    @GetMapping
    public Flux<ProjectRecord> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<ProjectRecord> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProjectRecord> create(@RequestBody ProjectRecord body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<ProjectRecord> update(@PathVariable Long id, @RequestBody ProjectRecord body) {
        return updateUseCase.update(id, body);
    }
}
