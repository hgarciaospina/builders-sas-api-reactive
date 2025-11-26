package com.builderssas.api.infrastructure.web.mapper;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.infrastructure.web.dto.notification.NotificationDto;

public final class NotificationWebMapper {

    private NotificationWebMapper() {
    }

    /**
     * Converts domain record -> web DTO
     */
    public static NotificationDto toWeb(NotificationRecord domain) {
        return new NotificationDto(
                domain.id(),
                domain.userId(),
                domain.eventType(),
                domain.payload(),
                domain.read(),
                domain.createdAt()
        );
    }
}
