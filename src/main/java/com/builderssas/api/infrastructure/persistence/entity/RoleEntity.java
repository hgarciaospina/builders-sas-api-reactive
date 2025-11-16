package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un rol dentro del sistema.
 *
 * Su responsabilidad es reflejar directamente la estructura de la tabla
 * "roles" en la base de datos. No contiene lógica de negocio, manteniendo
 * la separación de responsabilidades dentro de la Arquitectura Hexagonal.
 */
@Table("roles")
public class RoleEntity {

    /** Identificador único del rol. */
    @Id
    @Column("id")
    private Long id;

    /** Nombre del rol (ej.: "ROLE_ADMIN", "ROLE_ARCHITECT", "ROLE_USER"). */
    @Column("name")
    private String name;

    /** Descripción explicativa del rol. */
    @Column("description")
    private String description;

    /**
     * Indicador lógico que determina si el rol está activo dentro del sistema.
     * Declarado como Boolean para mantener consistencia con el dominio y con
     * el manejo de nullability en entornos reactivos.
     */
    @Column("active")
    private Boolean active;

    // ============================================================================================
    // GETTERS & SETTERS — estandarizados con los mappers y records del dominio
    // ============================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter correcto y estandarizado para el campo "active".
     * Obligatorio para permitir el mapeo limpio desde los adaptadores.
     */
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
