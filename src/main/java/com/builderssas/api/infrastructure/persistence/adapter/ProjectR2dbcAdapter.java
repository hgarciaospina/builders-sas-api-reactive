package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.project.Project;
import com.builderssas.api.domain.port.out.ProjectRepository;
import com.builderssas.api.infrastructure.persistence.entity.ProjectEntity;
import com.builderssas.api.infrastructure.persistence.repository.ProjectR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProjectR2dbcAdapter implements ProjectRepository {

    private final ProjectR2dbcRepository repository;


private Project toDomain(ProjectEntity e) {
    if (e == null) return null;
    return new Project(
        e.getId(),
            e.getName(),
        e.getCode(),
        e.getActive()
    );
}

private ProjectEntity toEntity(Project d) {
    if (d == null) return null;
    ProjectEntity e = new ProjectEntity();
    e.setId(d.id());
    e.setName(d.name());
    e.setCode(d.code());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<Project> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Project> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<Project> save(Project aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
