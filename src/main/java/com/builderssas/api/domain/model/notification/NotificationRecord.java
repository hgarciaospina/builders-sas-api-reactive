package com.builderssas.api.domain.model.notification;

import java.time.LocalDateTime;

/**
 * Immutable domain-level notification object.
 *
 * Used across application and persistence layers.
 */
public record NotificationRecord(
        Long id,
        Long userId,
        String eventType,
        String payload,
        Boolean read,
        LocalDateTime createdAt
) {}
