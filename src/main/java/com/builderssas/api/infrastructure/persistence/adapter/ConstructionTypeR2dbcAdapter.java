package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeEntity;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionTypeMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC que implementa el puerto de salida para tipos de construcción.
 *
 * 100% reactivo, sin lógica de negocio y alineado con la Arquitectura Hexagonal.
 * Se encarga únicamente de:
 *  - Invocar el repositorio R2DBC.
 *  - Convertir entre Entity (infraestructura) y Record (dominio).
 */
@Component
@RequiredArgsConstructor
public class ConstructionTypeR2dbcAdapter implements ConstructionTypeRepositoryPort {

    private final ConstructionTypeR2dbcRepository repository;
    private final ConstructionTypeMapper mapper;

    /**
     * Busca un tipo de construcción por su identificador.
     *
     * El filtrado por activos se hace en el caso de uso.
     */
    @Override
    public Mono<ConstructionTypeRecord> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toRecord);
    }

    /**
     * Lista todos los tipos de construcción, activos e inactivos.
     */
    @Override
    public Flux<ConstructionTypeRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Lista únicamente los tipos de construcción activos.
     */
    @Override
    public Flux<ConstructionTypeRecord> findAllActive() {
        return repository.findAll()
                .filter(entity -> Boolean.TRUE.equals(entity.getActive()))
                .map(mapper::toRecord);
    }

    /**
     * Persiste un tipo de construcción.
     *
     * Si el id es null, se crea un nuevo registro.
     * Si el id tiene valor, se actualiza el existente.
     */
    @Override
    public Mono<ConstructionTypeRecord> save(ConstructionTypeRecord aggregate) {
        return Mono.just(aggregate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }

    /**
     * Aplica borrado lógico (soft delete) para el tipo de construcción.
     *
     * No elimina físicamente el registro; crea una nueva instancia
     * inmutable con active = false y la guarda.
     */
    @Override
    public Mono<Void> softDelete(Long id) {
        return repository.findById(id)
                .map(entity ->
                        new ConstructionTypeEntity(
                                entity.getId(),
                                entity.getName(),
                                entity.getEstimatedDays(),
                                Boolean.FALSE // inactivar sin usar setters
                        )
                )
                .flatMap(repository::save)
                .then();
    }
}
