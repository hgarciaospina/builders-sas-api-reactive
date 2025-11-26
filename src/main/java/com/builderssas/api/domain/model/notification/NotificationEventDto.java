package com.builderssas.api.domain.model.notification;

import lombok.Builder;
import java.time.LocalDateTime;

/**
 * Internal DTO used to trigger domain notifications.
 * Not exposed to API clients.
 */
@Builder
public record NotificationEventDto(
        Long userId,
        String eventType,
        String payload,
        LocalDateTime timestamp
) {}
