package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente e inmutable que representa la relación usuario-rol en la base de datos.
 *
 * Esta clase pertenece a la capa de Infraestructura dentro de la Arquitectura Hexagonal.
 * Refleja exactamente la estructura de la tabla "user_roles" y se utiliza exclusivamente
 * para la persistencia mediante Spring Data R2DBC.
 *
 * Características:
 * - Totalmente inmutable: todos los campos son final.
 * - No tiene setters ni constructor vacío.
 * - Spring Data R2DBC utiliza el constructor completo para instanciar la entidad.
 * - Los getters permiten el acceso de lectura requerido por el repositorio reactivo.
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
public class UserRoleEntity {

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
    private final Boolean active;

    /**
     * Constructor completo requerido por Spring Data R2DBC para instanciar entidades inmutables.
     */
    public UserRoleEntity(Long id,
                          Long userId,
                          Long roleId,
                          LocalDateTime assignedAt,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          Boolean active) {

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

    public Boolean getActive() {
        return active;
    }
}
