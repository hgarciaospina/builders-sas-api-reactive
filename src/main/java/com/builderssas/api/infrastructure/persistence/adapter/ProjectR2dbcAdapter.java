package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.project.ProjectRecord;
import com.builderssas.api.domain.port.out.project.ProjectRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.ProjectMapper;
import com.builderssas.api.infrastructure.persistence.repository.ProjectR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC que implementa ProjectRepositoryPort y actúa como puente
 * entre el dominio (ProjectRecord) y la infraestructura (ProjectEntity).
 *
 * - 100% funcional
 * - 0% imperativa
 * - Sin lógica de negocio
 * - Conversión pura mediante mapper estático
 */
@Component
@RequiredArgsConstructor
public final class ProjectR2dbcAdapter implements ProjectRepositoryPort {

    private final ProjectR2dbcRepository repository;

    @Override
    public Mono<ProjectRecord> save(ProjectRecord record) {
        return Mono.just(record)
                .map(ProjectMapper::toEntity)
                .flatMap(repository::save)
                .map(ProjectMapper::toDomain);
    }

    @Override
    public Mono<ProjectRecord> findById(Long id) {
        return repository.findById(id)
                .map(ProjectMapper::toDomain);
    }

    @Override
    public Flux<ProjectRecord> findAll() {
        return repository.findAllActive()   // ← SOLO activos
                .map(ProjectMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Long> nextProjectCode() {
        return repository.nextProjectCode();
    }

    /**
     * Borrado lógico: active = false
     */
    @Override
    public Mono<Void> softDelete(Long id) {
        return repository.softDelete(id);
    }
}
