package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un tipo de material.
 *
 * Esta clase forma parte de la capa de Infraestructura dentro de la
 * Arquitectura Hexagonal. Su única responsabilidad es reflejar la
 * estructura de la tabla "material_types" para R2DBC.
 *
 * Características:
 *  • 100% inmutable (sin setters ni mutaciones).
 *  • Campos finales para garantizar consistencia.
 *  • Constructor completo anotado con @PersistenceCreator.
 *  • Sin constructor vacío.
 *
 * No contiene lógica de negocio.
 */
@Table("material_types")
public class MaterialTypeEntity {

    @Id
    @Column("id")
    private final Long id;

    /** Código único del material (CE, AR, GR, etc.) */
    @Column("code")
    private final String code;

    /** Nombre del material (Cemento, Arena, etc.) */
    @Column("name")
    private final String name;

    /** Unidad de medida (kg, m3, unidades, etc.) */
    @Column("unit_of_measure")
    private final String unitOfMeasure;

    /** Indicador de si el material está activo. */
    @Column("active")
    private final Boolean active;

    /**
     * Constructor completo utilizado por Spring Data R2DBC.
     * La anotación @PersistenceCreator indica que este es
     * el constructor que se debe usar para hidratar la entidad.
     */
    public MaterialTypeEntity(
            Long id,
            String code,
            String name,
            String unitOfMeasure,
            Boolean active
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.active = active;
    }

    // ================================
    // GETTERS (sin setters)
    // ================================
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Boolean getActive() {
        return active;
    }
}
