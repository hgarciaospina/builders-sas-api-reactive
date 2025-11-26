package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("notifications")
public final class NotificationEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("user_id")
    private final Long userId;

    @Column("event_type")
    private final String eventType;

    @Column("payload")
    private final String payload;

    @Column("is_read")
    private final Boolean read;

    @Column("created_at")
    private final LocalDateTime createdAt;

    public NotificationEntity(
            Long id,
            Long userId,
            String eventType,
            String payload,
            Boolean read,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.payload = payload;
        this.read = read;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getEventType() { return eventType; }
    public String getPayload() { return payload; }
    public Boolean getRead() { return read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
