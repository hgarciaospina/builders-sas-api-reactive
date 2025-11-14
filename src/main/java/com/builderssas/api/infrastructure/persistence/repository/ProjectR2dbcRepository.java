package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProjectR2dbcRepository extends ReactiveCrudRepository<ProjectEntity, Long> {
}
