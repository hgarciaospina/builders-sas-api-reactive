package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente que representa la relación usuario-rol en la base de datos.
 *
 * Esta clase pertenece a la capa de Infraestructura dentro de la Arquitectura Hexagonal.
 * Refleja exactamente la estructura de la tabla "user_roles" y se utiliza únicamente
 * para interacción con la base de datos mediante Spring Data R2DBC.
 *
 * Características:
 * - Constructor vacío requerido por R2DBC.
 * - Constructor completo utilizado por el Adapter para evitar lógica imperativa.
 * - Modelo mutable porque esta capa es técnica (no de dominio).
 */
@Table("user_roles")
public class UserRoleEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("role_id")
    private Long roleId;

    @Column("assigned_at")
    private LocalDateTime assignedAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("active")
    private Boolean active;

    // -------------------------------------------------------
    // Constructor vacío obligatorio para R2DBC
    // -------------------------------------------------------
    public UserRoleEntity() {}

    // -------------------------------------------------------
    // Constructor completo (para mapeo funcional sin imperativa)
    // -------------------------------------------------------
    public UserRoleEntity(
            Long id,
            Long userId,
            Long roleId,
            LocalDateTime assignedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Boolean active
    ) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.assignedAt = assignedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

    // -------------------------------------------------------
    // Getters & Setters (infraestructura técnica)
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
