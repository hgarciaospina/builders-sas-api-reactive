package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.MaterialTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repositorio R2DBC para MaterialTypeEntity.
 *
 * No contiene filtros adicionales.
 * No incluye métodos custom.
 * El filtrado por activos se aplica únicamente en los casos de uso.
 */
public interface MaterialTypeR2dbcRepository
        extends ReactiveCrudRepository<MaterialTypeEntity, Long> {
}
