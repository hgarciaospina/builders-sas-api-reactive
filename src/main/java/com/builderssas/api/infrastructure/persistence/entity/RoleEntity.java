package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un rol del sistema.
 *
 * Esta clase pertenece únicamente a la capa de infraestructura,
 * reflejando exactamente la estructura de la tabla "roles".
 *
 * - Inmutable
 * - Sin lógica de negocio
 * - Compatible con R2DBC
 */
@Table("roles")
public final class RoleEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("name")
    private final String name;

    @Column("description")
    private final String description;

    @Column("active")
    private final Boolean active;

    /**
     * Constructor completo obligatorio para R2DBC.
     */
    public RoleEntity(Long id, String name, String description, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    // ============================
    // GETTERS (sin setters)
    // ============================

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Boolean getActive() { return active; }

}