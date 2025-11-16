package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.materialtype.MaterialTypeRecord;
import com.builderssas.api.domain.port.out.materialtype.MaterialTypeRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.MaterialTypeEntity;
import com.builderssas.api.infrastructure.persistence.repository.MaterialTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Adaptador R2DBC encargado de implementar el puerto de salida
 * {@link MaterialTypeRepositoryPort} para la persistencia de tipos de material.
 *
 * Forma parte de la capa de infraestructura y se limita a:
 *   • Convertir entre Entity ↔ Record de dominio
 *   • Delegar operaciones al repositorio reactivo
 */
@Component
@RequiredArgsConstructor
public class MaterialTypeR2dbcAdapter implements MaterialTypeRepositoryPort {

    private final MaterialTypeR2dbcRepository repository;

    // ============================================================================
    // MAPPERS FUNCIONALES — (0% imperativa)
    // ============================================================================

    private MaterialTypeRecord toDomain(MaterialTypeEntity e) {
        return Optional.ofNullable(e)
                .map(x -> new MaterialTypeRecord(
                        x.getId(),
                        x.getName(),
                        x.getUnitOfMeasure(),
                        x.getActive()
                ))
                .orElse(null);
    }

    private MaterialTypeEntity toEntity(MaterialTypeRecord d) {
        return Optional.ofNullable(d)
                .map(x -> new MaterialTypeEntity(
                        x.id(),
                        x.name(),
                        x.unitOfMeasure(),
                        x.active()
                ))
                .orElse(null);
    }

    // ============================================================================
    // CRUD REACTIVO — Implementación del Puerto
    // ============================================================================

    @Override
    public Mono<MaterialTypeRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<MaterialTypeRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<MaterialTypeRecord> save(MaterialTypeRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
