package com.builderssas.api.infrastructure.notification;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.domain.port.out.notification.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In-memory implementation of {@link NotificationRepositoryPort}.
 *
 * This adapter replicates the legacy behavior of storing notifications
 * in an in-application storage instead of using a persistent database.
 *
 * Characteristics:
 *  • Thread-safe using concurrent collections.
 *  • Non-blocking from a WebFlux perspective.
 *  • Fully compatible with the hexagonal architecture.
 */
@Component
@RequiredArgsConstructor
public class InMemoryNotificationStorageAdapter implements NotificationRepositoryPort {

    /**
     * Global notifications (visible for everyone).
     */
    private final List<NotificationRecord> globalNotifications = new CopyOnWriteArrayList<>();

    /**
     * User-specific notifications.
     */
    private final Map<Long, List<NotificationRecord>> userNotifications = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> storeGlobal(NotificationRecord notification) {
        return Mono.fromRunnable(() -> globalNotifications.add(notification));
    }

    @Override
    public Mono<Void> storeForUser(NotificationRecord notification, Long userId) {
        return Mono.fromRunnable(() ->
                Optional.ofNullable(userId)
                        .map(id ->
                                userNotifications.computeIfAbsent(
                                        id,
                                        __ -> new CopyOnWriteArrayList<>()
                                )
                        )
                        .ifPresent(list -> list.add(notification))
        );
    }

    @Override
    public Flux<NotificationRecord> getAllGlobal() {
        return Flux.fromIterable(globalNotifications);
    }

    @Override
    public Flux<NotificationRecord> getByUser(Long userId) {
        return Optional.ofNullable(userId)
                .map(id -> userNotifications.getOrDefault(id, List.of()))
                .map(Flux::fromIterable)
                .orElseGet(Flux::empty);
    }
    @Override
    public Mono<Void> markAsRead(Long id) {
        return Mono.fromRunnable(() -> {
            globalNotifications.stream()
                    .filter(n -> n.id().equals(id))
                    .findFirst()
                    .ifPresent(old -> {
                        globalNotifications.remove(old);
                        globalNotifications.add(
                                new NotificationRecord(
                                        old.id(),
                                        old.userId(),
                                        old.eventType(),
                                        old.payload(),
                                        true,
                                        old.createdAt()
                                )
                        );
                    });

            userNotifications.values().forEach(list ->
                    list.stream()
                            .filter(n -> n.id().equals(id))
                            .findFirst()
                            .ifPresent(old -> {
                                list.remove(old);
                                list.add(
                                        new NotificationRecord(
                                                old.id(),
                                                old.userId(),
                                                old.eventType(),
                                                old.payload(),
                                                true,
                                                old.createdAt()
                                        )
                                );
                            })
            );
        });
    }

}
