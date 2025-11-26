package com.builderssas.api.infrastructure.persistence.repository;

import com.builderssas.api.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Reactive R2DBC repository for Notification persistence.
 *
 * Follows the Builders-SAS standard:
 *  • ReactiveCrudRepository base
 *  • Only DB access, no business logic
 *  • Mirrors the structure used in ConstructionOrderR2dbcRepository
 */
public interface NotificationR2dbcRepository
        extends ReactiveCrudRepository<NotificationEntity, Long> {

    /**
     * Returns all notifications belonging to a specific user.
     *
     * @param userId target user
     * @return Flux of NotificationEntity
     */
    Flux<NotificationEntity> findByUserId(Long userId);
}
