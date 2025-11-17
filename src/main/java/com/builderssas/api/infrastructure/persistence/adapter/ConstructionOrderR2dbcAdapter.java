package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionOrderMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionOrderR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para operaciones de persistencia de órdenes
 * de construcción dentro de la Arquitectura Hexagonal.
 *
 * Ajustado para alinearse completamente con los nombres
 * de métodos reales definidos en el repositorio R2DBC.
 */
@Component
@RequiredArgsConstructor
public class ConstructionOrderR2dbcAdapter implements ConstructionOrderRepositoryPort {

    private final ConstructionOrderR2dbcRepository repository;
    private final ConstructionOrderMapper mapper;

    @Override
    public Mono<ConstructionOrderRecord> findById(Long id) {
        return Mono.just(id)
                .flatMap(repository::findById)
                .map(mapper::toRecord);
    }

    /**
     * Port: findByProjectId()
     * Repo real: findAllByProjectId()
     */
    @Override
    public Flux<ConstructionOrderRecord> findByProjectId(Long projectId) {
        return Mono.just(projectId)
                .flatMapMany(repository::findAllByProjectId)
                .map(mapper::toRecord);
    }

    /**
     * Port: findLastByProjectId()
     * Repo real: findTopByProjectIdOrderByScheduledEndDateDesc()
     */
    @Override
    public Mono<ConstructionOrderRecord> findLastByProjectId(Long projectId) {
        return Mono.just(projectId)
                .flatMap(repository::findTopByProjectIdOrderByScheduledEndDateDesc)
                .map(mapper::toRecord);
    }

    @Override
    public Flux<ConstructionOrderRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    @Override
    public Mono<ConstructionOrderRecord> save(ConstructionOrderRecord aggregate) {
        return Mono.just(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }
}
