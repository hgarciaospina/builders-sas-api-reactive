package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente para control de tokens de autenticación.
 *
 * <p>Esta entidad soporta necesidades enterprise:
 * expiración server-side, revocación inmediata, auditoría y control de sesiones.</p>
 *
 * <p>Capa de infraestructura: no es modelo de dominio.</p>
 */
@Table("auth_tokens")
public final class AuthTokenEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("user_id")
    private final Long userId;

    @Column("token")
    private final String token;

    @Column("token_type")
    private final String tokenType;

    @Column("issued_at")
    private final LocalDateTime issuedAt;

    @Column("expires_at")
    private final LocalDateTime expiresAt;

    @Column("revoked")
    private final Boolean revoked;

    @Column("created_at")
    private final LocalDateTime createdAt;

    public AuthTokenEntity(
            Long id,
            Long userId,
            String token,
            String tokenType,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt,
            Boolean revoked,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.tokenType = tokenType;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }

    public Long getUserId() { return userId; }

    public String getToken() { return token; }

    public String getTokenType() { return tokenType; }

    public LocalDateTime getIssuedAt() { return issuedAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }

    public Boolean getRevoked() { return revoked; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}