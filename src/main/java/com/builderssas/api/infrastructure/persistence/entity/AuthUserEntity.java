package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente para credenciales de autenticación.
 * No duplica información del usuario. Solo almacena
 * la contraseña asociada al user_id de la tabla users.
 */
@Table("auth_users")
public final class AuthUserEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("user_id")
    private final Long userId;

    @Column("password_hash")
    private final String passwordHash;

    @Column("active")
    private final Boolean active;

    @Column("created_at")
    private final LocalDateTime createdAt;

    @Column("updated_at")
    private final LocalDateTime updatedAt;

    public AuthUserEntity(
            Long id,
            Long userId,
            String passwordHash,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getPasswordHash() { return passwordHash; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
