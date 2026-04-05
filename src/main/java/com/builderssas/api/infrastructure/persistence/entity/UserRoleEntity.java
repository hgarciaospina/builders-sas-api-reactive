package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente e inmutable que representa la relación usuario-rol.
 *
 * Pertenece a la capa de Infraestructura dentro de la Arquitectura Hexagonal.
 * Su única responsabilidad es mapear la tabla "user_roles".
 *
 * Reglas de diseño:
 * - Inmutable: todos los campos son final
 * - Sin setters
 * - Constructor completo requerido por Spring Data R2DBC
 * - No contiene lógica de negocio (solo persistencia)
 *
 * Consideraciones importantes:
 * - El atributo "active" se modela como primitivo boolean para evitar nulls
 * - Se expone mediante el método isActive() siguiendo estándar JavaBeans
 * - Permite uso limpio en programación funcional (WebFlux)
 *
 * Columnas mapeadas:
 * - id          → id
 * - userId      → user_id
 * - roleId      → role_id
 * - assignedAt  → assigned_at
 * - createdAt   → created_at
 * - updatedAt   → updated_at
 * - active      → active
 */
@Table("user_roles")
public final class UserRoleEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("user_id")
    private final Long userId;

    @Column("role_id")
    private final Long roleId;

    @Column("assigned_at")
    private final LocalDateTime assignedAt;

    @Column("created_at")
    private final LocalDateTime createdAt;

    @Column("updated_at")
    private final LocalDateTime updatedAt;

    @Column("active")
    private final boolean active;

    /**
     * Constructor completo requerido por Spring Data R2DBC
     * para entidades inmutables.
     */
    public UserRoleEntity(Long id,
                          Long userId,
                          Long roleId,
                          LocalDateTime assignedAt,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          boolean active) {

        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.assignedAt = assignedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Indica si la relación usuario-rol está activa.
     *
     * @return true si está activa, false si fue desactivada (soft delete)
     */
    public boolean isActive() {
        return active;
    }
}