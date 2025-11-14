package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ConstructionOrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ConstructionOrderR2dbcRepository extends ReactiveCrudRepository<ConstructionOrderEntity, Long> {
}
