package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un proyecto dentro del sistema.
 *
 * Su responsabilidad es mapear directamente la estructura de la tabla
 * "projects" en la base de datos. No contiene lógica de negocio,
 * preservando la separación de responsabilidades del modelo de dominio.
 */
@Table("projects")
public class ProjectEntity {

    /** Identificador único del proyecto. */
    @Id
    @Column("id")
    private Long id;

    /** Nombre descriptivo del proyecto. */
    @Column("name")
    private String name;

    /** Código único del proyecto. */
    @Column("code")
    private String code;

    /**
     * Indicador lógico que refleja si el proyecto se encuentra activo.
     * Se mantiene como Boolean (no boolean) para respetar la
     * consistencia del dominio y evitar problemas de nullability.
     */
    @Column("active")
    private Boolean active;

    // ============================================================================================
    // GETTERS & SETTERS  — estandarizados con el resto de la arquitectura
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter estándar para el campo "active".
     * Debe ser getActive() (NO isActive) para mantener coherencia
     * con los adaptadores y records del dominio.
     */
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
