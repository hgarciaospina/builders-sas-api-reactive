package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionRequestMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionRequestR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para persistencia de ConstructionRequest.
 *
 * Implementa el puerto de salida ConstructionRequestRepositoryPort
 * delegando en el repositorio reactivo y aplicando conversión
 * Entity ↔ Record mediante ConstructionRequestMapper.
 *
 *  - 0% imperativa
 *  - 0% setters
 *  - 0% lógica de negocio
 *  - 100% funcional + hexagonal
 */
@Component
@RequiredArgsConstructor
public class ConstructionRequestR2dbcAdapter implements ConstructionRequestRepositoryPort {

    private final ConstructionRequestR2dbcRepository repository;
    private final ConstructionRequestMapper mapper;

    @Override
    public Mono<ConstructionRequestRecord> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toRecord);
    }

    @Override
    public Flux<ConstructionRequestRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    @Override
    public Mono<ConstructionRequestRecord> save(ConstructionRequestRecord aggregate) {
        return Mono.just(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }

    @Override
    public Flux<ConstructionRequestRecord> findByRequestedByUserId(Long userId) {
        return repository.findByRequestedByUserId(userId)
                .map(mapper::toRecord);
    }

    @Override
    public Flux<ConstructionRequestRecord> findByProjectId(Long projectId) {
        return repository.findByProjectId(projectId)
                .map(mapper::toRecord);
    }
}
