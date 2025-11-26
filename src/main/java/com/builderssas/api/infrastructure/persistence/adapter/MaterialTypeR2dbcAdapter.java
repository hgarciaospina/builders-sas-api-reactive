package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.MaterialTypeEntity;
import com.builderssas.api.infrastructure.persistence.mapper.MaterialTypeMapper;
import com.builderssas.api.infrastructure.persistence.repository.MaterialTypeR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para MaterialType.
 *
 * 100% reactivo y sin lógica de negocio.
 * El filtrado por activos se aplica únicamente en los casos de uso.
 */
@Component
@RequiredArgsConstructor
public class MaterialTypeR2dbcAdapter implements MaterialTypeRepositoryPort {

    private final MaterialTypeR2dbcRepository repository;
    private final MaterialTypeMapper mapper;

    /**
     * Busca un tipo de material por su ID.
     * El caso de uso decide si filtra activos.
     */
    @Override
    public Mono<MaterialTypeRecord> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toRecord);
    }

    /**
     * Lista todos los tipos de material (activos + inactivos).
     */
    @Override
    public Flux<MaterialTypeRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Lista solo materiales activos.
     */
    @Override
    public Flux<MaterialTypeRecord> findAllActive() {
        return repository.findAll()
                .filter(entity -> Boolean.TRUE.equals(entity.getActive()))
                .map(mapper::toRecord);
    }

    /**
     * Crea o actualiza un material.
     */
    @Override
    public Mono<MaterialTypeRecord> save(MaterialTypeRecord record) {
        return Mono.just(record)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }

    /**
     * Soft delete: marca el material como inactivo.
     */
    @Override
    public Mono<Void> softDelete(Long id) {
        return repository.findById(id)
                .map(entity ->
                        new MaterialTypeEntity(
                                entity.getId(),
                                entity.getCode(),        // <── FALTABA
                                entity.getName(),
                                entity.getUnitOfMeasure(),
                                Boolean.FALSE            // <── se inactiva
                        )
                )
                .flatMap(repository::save)
                .then();
    }
}
