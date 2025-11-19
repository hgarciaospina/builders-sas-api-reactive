package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeMaterialEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio R2DBC para ConstructionTypeMaterial.
 *
 * Extiende ReactiveCrudRepository para proporcionar las operaciones
 * básicas de persistencia (findAll, findById, save, delete, etc.)
 * de forma 100% reactiva.
 *
 * No contiene lógica de negocio.
 */
@Repository
public interface ConstructionTypeMaterialR2dbcRepository
        extends ReactiveCrudRepository<ConstructionTypeMaterialEntity, Long> {
}
