package com.builderssas.api.domain.port.in.notification;

import reactor.core.publisher.Mono;

public interface MarkNotificationAsReadUseCase {
    Mono<Void> markAsRead(Long notificationId);
}
