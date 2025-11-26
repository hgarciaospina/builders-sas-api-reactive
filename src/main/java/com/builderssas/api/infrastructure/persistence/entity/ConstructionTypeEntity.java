package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un tipo de construcción.
 *
 * Estándar oficial del proyecto:
 *  • 100% inmutable
 *  • Todos los campos final
 *  • Constructor vacío requerido por R2DBC
 *  • Constructor completo con @PersistenceCreator
 *  • Cero setters
 *  • Sin lógica de negocio
 */
@Table("construction_types")
public class ConstructionTypeEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("name")
    private final String name;

    @Column("estimated_days")
    private final Integer estimatedDays;

    @Column("active")
    private final Boolean active;

    /**
     * Constructor vacío requerido por Spring R2DBC.
     * No usar manualmente.
     */
    public ConstructionTypeEntity() {
        this.id = null;
        this.name = null;
        this.estimatedDays = null;
        this.active = null;
    }

    /**
     * Constructor completo inmutable usado por Spring y los mappers.
     */
    @PersistenceCreator
    public ConstructionTypeEntity(
            Long id,
            String name,
            Integer estimatedDays,
            Boolean active
    ) {
        this.id = id;
        this.name = name;
        this.estimatedDays = estimatedDays;
        this.active = active;
    }

    // Getters inmutables
    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getEstimatedDays() { return estimatedDays; }
    public Boolean getActive() { return active; }
}
