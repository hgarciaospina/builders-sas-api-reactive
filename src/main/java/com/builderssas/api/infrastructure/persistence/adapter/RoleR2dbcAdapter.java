package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import com.builderssas.api.infrastructure.persistence.repository.RoleR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para RoleEntity.
 */
@Component
@RequiredArgsConstructor
public class RoleR2dbcAdapter implements RoleRepositoryPort {

    private final RoleR2dbcRepository repository;

    // ============================
    //        MAPPERS
    // ============================

    private RoleRecord toDomain(RoleEntity e) {
        return new RoleRecord(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getActive()
        );
    }

    private RoleEntity toEntity(RoleRecord d) {
        return new RoleEntity(
                d.id(),
                d.name(),
                d.description(),
                d.active()
        );
    }

    // ============================
    //     IMPLEMENTACIÓN PORT
    // ============================

    @Override
    public Mono<RoleRecord> save(RoleRecord role) {
        return repository.save(toEntity(role)).map(this::toDomain);
    }

    @Override
    public Flux<RoleRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<RoleRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<RoleRecord> findByName(String name) {
        return repository.findByName(name).map(this::toDomain);
    }

    @Override
    public Mono<RoleRecord> findByDescription(String description) {
        return repository.findByDescription(description).map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Boolean> existsByDescription(String description) {
        return repository.existsByDescription(description);
    }

    @Override
    public Mono<RoleRecord> update(RoleRecord role) {
        return repository.save(toEntity(role)).map(this::toDomain);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
