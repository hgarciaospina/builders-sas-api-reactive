package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.InventoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface InventoryR2dbcRepository
        extends ReactiveCrudRepository<InventoryEntity, Long> {

    Mono<InventoryEntity> findByMaterialId(Long materialId);

    Mono<Void> deleteByMaterialId(Long materialId);
}
