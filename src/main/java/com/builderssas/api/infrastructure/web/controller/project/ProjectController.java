package com.builderssas.api.infrastructure.web.controller.project;

import com.builderssas.api.domain.port.in.project.CreateProjectUseCase;
import com.builderssas.api.domain.port.in.project.UpdateProjectUseCase;
import com.builderssas.api.domain.port.in.project.GetProjectByIdUseCase;
import com.builderssas.api.domain.port.in.project.GetAllProjectsUseCase;
import com.builderssas.api.domain.port.in.project.DeleteProjectUseCase;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;

import com.builderssas.api.infrastructure.web.dto.project.CreateProjectDto;
import com.builderssas.api.infrastructure.web.dto.project.UpdateProjectDto;
import com.builderssas.api.infrastructure.web.dto.project.ProjectDto;
import com.builderssas.api.infrastructure.web.mapper.ProjectWebMapper;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController

@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public final class ProjectController {

    private final CreateProjectUseCase createUseCase;
    private final UpdateProjectUseCase updateUseCase;
    private final GetProjectByIdUseCase getByIdUseCase;
    private final GetAllProjectsUseCase getAllUseCase;
    private final DeleteProjectUseCase deleteUseCase;
    private final ProjectRepositoryPort projectRepositoryPort;
    private final ProjectWebMapper mapper;

    @PostMapping
    public Mono<ProjectDto> create(@RequestBody @Valid CreateProjectDto dto) {

        Long createdByUserId = 1L;

        return projectRepositoryPort.nextProjectCode()
                .flatMap(seq -> mapper.toDomain(dto, createdByUserId, Mono.just(seq)))
                .flatMap(createUseCase::create)
                .map(record -> mapper.toDto(record, null));
    }

    @PutMapping("/{id}")
    public Mono<ProjectDto> update(@PathVariable Long id,
                                   @RequestBody @Valid UpdateProjectDto dto) {

        return getByIdUseCase.getById(id)
                .flatMap(existing ->
                        mapper.toDomain(dto, existing, Mono.just(existing.id()))
                                .flatMap(updateUseCase::update)
                                .map(updated -> mapper.toDto(updated, null))
                );
    }

    @GetMapping("/{id}")
    public Mono<ProjectDto> getById(@PathVariable Long id) {
        return getByIdUseCase.getById(id)
                .map(record -> mapper.toDto(record, null));
    }

    @GetMapping
    public Flux<ProjectDto> getAll() {
        return getAllUseCase.findAll()
                .map(record -> mapper.toDto(record, null));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
