package com.builderssas.api.domain.port.in.notification;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import reactor.core.publisher.Flux;

public interface GetUserNotificationsUseCase {

    Flux<NotificationRecord> getByUserId(Long userId);
}
