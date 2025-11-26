package com.builderssas.api.application.usecase.notification;

import com.builderssas.api.domain.port.in.notification.MarkNotificationAsReadUseCase;
import com.builderssas.api.domain.port.out.notification.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Marks a notification as read.
 */
@Service
@RequiredArgsConstructor
public class MarkNotificationAsReadService implements MarkNotificationAsReadUseCase {

    private final NotificationRepositoryPort repository;

    @Override
    public Mono<Void> markAsRead(Long id) {
        return repository.markAsRead(id);
    }
}
