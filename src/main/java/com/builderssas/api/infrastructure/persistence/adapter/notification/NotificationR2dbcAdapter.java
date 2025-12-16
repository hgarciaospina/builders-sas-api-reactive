package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.domain.port.out.notification.NotificationRepositoryPort;
import com.builderssas.api.infrastructure.persistence.entity.NotificationEntity;
import com.builderssas.api.infrastructure.persistence.repository.NotificationR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Primary
@Component
@RequiredArgsConstructor
public class NotificationR2dbcAdapter implements NotificationRepositoryPort {

    private final NotificationR2dbcRepository repository;

    // ============================================================
    // STORE GLOBAL NOTIFICATION (userId = null)
    // ============================================================
    @Override
    public Mono<Void> storeGlobal(NotificationRecord notification) {
        NotificationEntity entity = new NotificationEntity(
                null,
                null, // global notification
                notification.eventType(),
                notification.payload(),
                false, // unread
                notification.createdAt()
        );

        return repository.save(entity).then();
    }

    // ============================================================
    // STORE NOTIFICATION FOR SPECIFIC USER
    // ============================================================
    @Override
    public Mono<Void> storeForUser(NotificationRecord notification, Long userId) {

        NotificationEntity entity = new NotificationEntity(
                null,
                userId,
                notification.eventType(),
                notification.payload(),
                false,
                notification.createdAt()
        );

        return repository.save(entity).then();
    }

    // ============================================================
    // GET ALL GLOBAL NOTIFICATIONS
    // ============================================================
    @Override
    public Flux<NotificationRecord> getAllGlobal() {
        return repository.findByUserId(null)
                .map(this::toRecord);
    }

    // ============================================================
    // GET NOTIFICATIONS BY USER
    // ============================================================
    @Override
    public Flux<NotificationRecord> getByUser(Long userId) {
        return repository.findByUserId(userId)
                .map(this::toRecord);
    }

    // ============================================================
    // MARK AS READ
    // ============================================================
    @Override
    public Mono<Void> markAsRead(Long id) {
        return repository.findById(id)
                .flatMap(entity -> repository.save(
                        new NotificationEntity(
                                entity.getId(),
                                entity.getUserId(),
                                entity.getEventType(),
                                entity.getPayload(),
                                true,
                                entity.getCreatedAt()
                        )
                ))
                .then();
    }

    // ============================================================
    // MAP ENTITY → RECORD
    // ============================================================
    private NotificationRecord toRecord(NotificationEntity e) {
        return new NotificationRecord(
                e.getId(),
                e.getUserId(),
                e.getEventType(),
                e.getPayload(),
                e.getRead(),
                e.getCreatedAt()
        );
    }
}
