package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ConstructionRequestEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ConstructionRequestR2dbcRepository extends ReactiveCrudRepository<ConstructionRequestEntity, Long> {
}
