package com.builderssas.api.infrastructure.persistence.mapper;

import com.builderssas.api.domain.model.notification.NotificationRecord;
import com.builderssas.api.infrastructure.persistence.entity.NotificationEntity;

public final class NotificationMapper {

    private NotificationMapper() {}

    public static NotificationRecord toDomain(NotificationEntity e) {
        return new NotificationRecord(
                e.getId(),
                e.getUserId(),
                e.getEventType(),
                e.getPayload(),
                e.getRead(),
                e.getCreatedAt()
        );
    }

    public static NotificationEntity toEntity(NotificationRecord r) {
        return new NotificationEntity(
                r.id(),
                r.userId(),
                r.eventType(),
                r.payload(),
                r.read(),
                r.createdAt()
        );
    }
}
