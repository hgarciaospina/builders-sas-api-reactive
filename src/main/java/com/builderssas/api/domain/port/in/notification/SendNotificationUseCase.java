package com.builderssas.api.domain.port.in.notification;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import reactor.core.publisher.Mono;

/**
 * Input port for sending system notifications.
 */
public interface SendNotificationUseCase {

    /**
     * Stores a new notification in the system.
     *
     * @param notification new notification record
     * @return Mono<void> when persisted
     */
    Mono<Void> send(NotificationRecord notification);
}
