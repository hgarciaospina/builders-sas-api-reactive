package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ConstructionTypeR2dbcRepository extends ReactiveCrudRepository<ConstructionTypeEntity, Long> {
}
