package com.builderssas.api.infrastructure.web.dto.notification;

import java.time.LocalDateTime;

/**
 * DTO used to expose notification data to API consumers.
 *
 * Pure output structure with no domain logic.
 */
public record NotificationDto(
        Long id,
        Long userId,
        String eventType,
        String payload,
        Boolean read,
        LocalDateTime createdAt
) {}
