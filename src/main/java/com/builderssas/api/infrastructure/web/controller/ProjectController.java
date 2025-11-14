package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.project.Project;
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
    public Flux<Project> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<Project> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Project> create(@RequestBody Project body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<Project> update(@PathVariable Long id, @RequestBody Project body) {
        return updateUseCase.update(id, body);
    }
}
