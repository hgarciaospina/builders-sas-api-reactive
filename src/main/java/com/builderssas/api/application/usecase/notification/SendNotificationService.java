package com.builderssas.api.application.usecase.notification;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.domain.port.in.notification.SendNotificationUseCase;
import com.builderssas.api.domain.port.out.notification.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Use case for sending notifications (global).
 *
 * This service does NOT store per-user messages.
 * For user-specific notifications, another use case should be used.
 */
@Service
@RequiredArgsConstructor
public class SendNotificationService implements SendNotificationUseCase {

    private final NotificationRepositoryPort repository;

    @Override
    public Mono<Void> send(NotificationRecord notification) {
        // Global notifications only
        return repository.storeGlobal(notification);
    }
}
