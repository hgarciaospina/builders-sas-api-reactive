package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un tipo de construcción.
 *
 * Esta clase forma parte de la capa de infraestructura y su única
 * responsabilidad es reflejar la estructura de la tabla en la base de datos.
 *
 * No incluye lógica de negocio; dicha lógica reside en el dominio.
 */
@Table("construction_types")
public class ConstructionTypeEntity {

    /** Identificador único del tipo de construcción. */
    @Id
    @Column("id")
    private Long id;

    /** Nombre del tipo de construcción (ej.: vivienda, muro, cerca). */
    @Column("name")
    private String name;

    /**
     * Duración estimada del proceso en días.
     * Este atributo suele representar un valor planificado.
     */
    @Column("estimated_days")
    private int estimatedDays;

    /**
     * Indicador de si el tipo de construcción está activo
     * para ser seleccionado en nuevas solicitudes.
     */
    @Column("active")
    private boolean active;

    // ============================================================================================
    // GETTERS & SETTERS
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

    public int getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(int estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public boolean isActive() {
        return active; // nombre correcto para booleanos
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
