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
 * Responsabilidades:
 *  - Mapear entre {@link MaterialTypeEntity} (infraestructura) y
 *    {@link MaterialTypeRecord} (dominio).
 *  - Delegar las operaciones reactivas al repositorio R2DBC.
 *
 * No introduce lógica de negocio ni programación imperativa.
 */
@Component
@RequiredArgsConstructor
public class MaterialTypeR2dbcAdapter implements MaterialTypeRepositoryPort {

    private final MaterialTypeR2dbcRepository repository;

    // ============================================================================
    // MAPEADORES FUNCIONALES — Entity ↔ Record
    // ============================================================================

    private MaterialTypeRecord toDomain(MaterialTypeEntity entity) {
        return Optional.ofNullable(entity)
                .map(e -> new MaterialTypeRecord(
                        e.getId(),
                        e.getName(),
                        e.getUnitOfMeasure(),
                        e.getActive()
                ))
                .orElse(null);
    }

    private MaterialTypeEntity toEntity(MaterialTypeRecord record) {
        return Optional.ofNullable(record)
                .map(r -> new MaterialTypeEntity(
                        r.id(),
                        r.name(),
                        r.unitOfMeasure(),
                        r.active()
                ))
                .orElse(null);
    }

    // ============================================================================
    // CRUD REACTIVO — Implementación del Puerto
    // ============================================================================

    /**
     * Recupera un tipo de material por su identificador.
     *
     * @param id identificador del tipo de material
     * @return Mono con el tipo encontrado o vacío si no existe
     */
    @Override
    public Mono<MaterialTypeRecord> findById(Long id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    /**
     * Recupera todos los tipos de material.
     *
     * @return Flux con todos los tipos de material
     */
    @Override
    public Flux<MaterialTypeRecord> findAll() {
        return repository.findAll()
                .map(this::toDomain);
    }

    /**
     * Persiste o actualiza un tipo de material.
     *
     * @param aggregate record de dominio a persistir
     * @return Mono con el tipo persistido
     */
    @Override
    public Mono<MaterialTypeRecord> save(MaterialTypeRecord aggregate) {
        return repository.save(toEntity(aggregate))
                .map(this::toDomain);
    }

    @Override
    public Mono<Boolean> hasSufficientStockForType(Long constructionTypeId) {
        // Implementación NO-OP temporal: siempre indica que hay stock suficiente.
        return Mono.just(Boolean.TRUE);
    }

    @Override
    public Mono<Void> discountStockForType(Long constructionTypeId) {
        // Implementación NO-OP temporal: no realiza cambios en la base de datos.
        return Mono.empty();
    }
}
