package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RoleR2dbcRepository extends ReactiveCrudRepository<RoleEntity, Long> {

    Mono<RoleEntity> findByName(String name);

    Mono<RoleEntity> findByDescription(String description);

    Mono<Boolean> existsByName(String name);

    Mono<Boolean> existsByDescription(String description);
}
