package com.builderssas.api.application.usecase.notification;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.domain.port.out.notification.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SendUserNotificationService {

    private final NotificationRepositoryPort storage;

    public Mono<Void> sendToUser(Long userId, String eventType, String payload) {
        return storage.storeForUser(
                new NotificationRecord(
                        null,
                        userId,
                        eventType,
                        payload,
                        false,
                        java.time.LocalDateTime.now()
                ),
                userId
        );
    }
}
