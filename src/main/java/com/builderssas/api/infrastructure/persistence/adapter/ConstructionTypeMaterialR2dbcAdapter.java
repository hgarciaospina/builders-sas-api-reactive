package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.out.constructiontypematerial.ConstructionTypeMaterialRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeMaterialEntity;
import com.builderssas.api.infrastructure.persistence.mapper.ConstructionTypeMaterialMapper;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionTypeMaterialR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Adaptador R2DBC para ConstructionTypeMaterial.
 *
 * Forma parte de la capa de Infraestructura dentro de la Arquitectura Hexagonal.
 * Su responsabilidad es realizar operaciones de persistencia 100% reactivas,
 * delegando toda la lógica de negocio a la capa de dominio/casos de uso.
 *
 * Características:
 *  • 100% funcional (sin setters, sin lógica de negocio).
 *  • Uso estricto de R2DBC + Reactor.
 *  • Conversión entre record y entity mediante el mapper.
 *  • Soft delete aplicado a nivel de bandera "active".
 */
@Component
@RequiredArgsConstructor
public class ConstructionTypeMaterialR2dbcAdapter implements ConstructionTypeMaterialRepositoryPort {

    private final ConstructionTypeMaterialR2dbcRepository repository;
    private final ConstructionTypeMaterialMapper mapper;

    /**
     * Busca un registro por su ID.
     */
    @Override
    public Mono<ConstructionTypeMaterialRecord> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toRecord);
    }

    /**
     * Lista todos los registros (activos + inactivos).
     */
    @Override
    public Flux<ConstructionTypeMaterialRecord> findAll() {
        return repository.findAll()
                .map(mapper::toRecord);
    }

    /**
     * Lista únicamente los activos.
     */
    @Override
    public Flux<ConstructionTypeMaterialRecord> findAllActive() {
        return repository.findAll()
                .filter(entity -> Boolean.TRUE.equals(entity.getActive()))
                .map(mapper::toRecord);
    }

    /**
     * Crea o actualiza un registro.
     */
    @Override
    public Mono<ConstructionTypeMaterialRecord> save(ConstructionTypeMaterialRecord record) {
        return Mono.just(record)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toRecord);
    }

    @Override
    public Flux<ConstructionTypeMaterialRecord> findByConstructionTypeId(Long constructionTypeId) {
        return repository.findByConstructionTypeId(constructionTypeId)
                .map(mapper::toRecord);
    }
    /**
     * Soft delete: marca el registro como inactivo.
     */
    @Override
    public Mono<Void> softDelete(Long id) {
        return repository.findById(id)
                .map(entity ->
                        new ConstructionTypeMaterialEntity(
                                entity.getId(),
                                entity.getConstructionTypeId(),
                                entity.getMaterialTypeId(),
                                entity.getQuantityRequired(),
                                Boolean.FALSE          // <── Se inactiva el registro
                        )
                )
                .flatMap(repository::save)
                .then();
    }
}
