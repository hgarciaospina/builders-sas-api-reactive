package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeEntity;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionTypeR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Adaptador R2DBC para la entidad ConstructionType.
 *
 * Pertenece a la capa de infraestructura dentro de la Arquitectura Hexagonal.
 * Su única responsabilidad es traducir entre:
 *
 *   • Entity (infraestructura)
 *   • Record del dominio (modelo puro)
 *
 * y delegar las operaciones reactivas al repositorio R2DBC.
 *
 * No contiene ninguna lógica de negocio.
 */
@Component
@RequiredArgsConstructor
public class ConstructionTypeR2dbcAdapter implements ConstructionTypeRepositoryPort {

    /** Repositorio R2DBC real que accede a la base de datos. */
    private final ConstructionTypeR2dbcRepository repository;

    // ============================================================================
    // MAPPERS — Functional Style (0% imperativa)
    // ============================================================================

    /**
     * Convierte una entity en su record de dominio equivalente.
     *
     * @param e entity de infraestructura
     * @return record del dominio o null
     */
    private ConstructionTypeRecord toDomain(ConstructionTypeEntity e) {
        return Optional.ofNullable(e)
                .map(x -> new ConstructionTypeRecord(
                        x.getId(),
                        x.getName(),
                        x.getEstimatedDays(),
                        x.isActive()
                ))
                .orElse(null);
    }

    /**
     * Convierte un record del dominio en una entity lista para persistencia.
     *
     * @param d record del dominio
     * @return entity persistible o null
     */
    private ConstructionTypeEntity toEntity(ConstructionTypeRecord d) {
        return Optional.ofNullable(d)
                .map(x -> {
                    ConstructionTypeEntity e = new ConstructionTypeEntity();
                    e.setId(x.id());
                    e.setName(x.name());
                    e.setEstimatedDays(x.estimatedDays());
                    e.setActive(x.active());
                    return e;
                })
                .orElse(null);
    }

    // ============================================================================
    // CRUD REACTIVO — Implementación del Puerto
    // ============================================================================

    /**
     * Busca un tipo de construcción por ID.
     *
     * @param id identificador
     * @return Mono con el record del dominio
     */
    @Override
    public Mono<ConstructionTypeRecord> findById(Long id) {
        return repository.findById(id)
                .map(this::toDomain);
    }

    /**
     * Obtiene todos los tipos de construcción disponibles.
     *
     * @return Flux con el catálogo
     */
    @Override
    public Flux<ConstructionTypeRecord> findAll() {
        return repository.findAll()
                .map(this::toDomain);
    }

    /**
     * Guarda o actualiza un tipo de construcción.
     *
     * @param aggregate record del dominio
     * @return Mono con el record persistido
     */
    @Override
    public Mono<ConstructionTypeRecord> save(ConstructionTypeRecord aggregate) {
        return repository.save(toEntity(aggregate))
                .map(this::toDomain);
    }
}
