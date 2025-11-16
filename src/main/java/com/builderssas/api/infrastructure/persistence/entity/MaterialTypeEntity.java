package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa los tipos de material.
 *
 * Se usa exclusivamente en la capa de infraestructura para mapear
 * la tabla "material_types" a la base de datos mediante R2DBC.
 *
 * No contiene lógica de negocio.
 */
@Table("material_types")
public class MaterialTypeEntity {

    /** Identificador único del tipo de material. */
    @Id
    @Column("id")
    private Long id;

    /** Nombre del material. */
    @Column("name")
    private String name;

    /** Unidad de medida del material (kg, m3, m2, etc). */
    @Column("unit_of_measure")
    private String unitOfMeasure;

    /** Indicador de si el material está activo. */
    @Column("active")
    private Boolean active;

    // -------------------------------------------------------
    // Constructor vacío requerido por R2DBC
    // -------------------------------------------------------
    public MaterialTypeEntity() {}

    // -------------------------------------------------------
    // Constructor completo
    // -------------------------------------------------------
    public MaterialTypeEntity(Long id, String name, String unitOfMeasure, Boolean active) {
        this.id = id;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.active = active;
    }

    // -------------------------------------------------------
    // Getters & Setters estandarizados
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
