package com.builderssas.api.infrastructure.web.controller.notification;

import com.builderssas.api.domain.port.in.notification.GetUserNotificationsUseCase;
import com.builderssas.api.domain.port.in.notification.MarkNotificationAsReadUseCase;
import com.builderssas.api.infrastructure.web.dto.notification.NotificationDto;
import com.builderssas.api.infrastructure.web.mapper.NotificationWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final GetUserNotificationsUseCase getUserNotificationsUseCase;
    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;

    @GetMapping("/{userId}")
    public Flux<NotificationDto> getNotifications(@PathVariable Long userId) {
        return getUserNotificationsUseCase.getByUserId(userId)
                .map(NotificationWebMapper::toWeb);
    }

    @PostMapping("/{notificationId}/read")
    public Mono<Void> markAsRead(@PathVariable Long notificationId) {
        return markNotificationAsReadUseCase.markAsRead(notificationId);
    }
}
