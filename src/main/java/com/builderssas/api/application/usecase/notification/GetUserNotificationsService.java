package com.builderssas.api.application.usecase.notification;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.domain.port.in.notification.GetUserNotificationsUseCase;
import com.builderssas.api.domain.port.out.notification.NotificationRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetUserNotificationsService implements GetUserNotificationsUseCase {

    private final NotificationRepositoryPort storage;

    @Override
    public Flux<NotificationRecord> getByUserId(Long userId) {
        return storage.getByUser(userId);
    }
}
