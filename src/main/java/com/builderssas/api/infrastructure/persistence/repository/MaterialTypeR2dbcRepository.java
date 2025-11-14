package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.MaterialTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MaterialTypeR2dbcRepository extends ReactiveCrudRepository<MaterialTypeEntity, Long> {
}
