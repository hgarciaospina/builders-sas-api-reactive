package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity (R2DBC) representing the association between a ConstructionType and a MaterialType.
 *
 * Esta tabla define la cantidad de un MaterialType requerido por un ConstructionType.
 *
 * ────────────────────────────────────────────────────────────────────────────────
 * DISEÑO:
 * - Inmutable: sin setters, sin @Builder, sin @NoArgsConstructor.
 * - No existen relaciones JPA (ManyToOne, JoinColumn). Solo IDs.
 * - Soft delete mediante bandera "active".
 * - Constructor completo obligatorio.
 * - Sin lógica de negocio en la capa de infraestructura.
 * - Integridad referencial se maneja a nivel de migraciones SQL (NO aquí).
 * ────────────────────────────────────────────────────────────────────────────────
 */
@Table("construction_type_materials")
public final class ConstructionTypeMaterialEntity {

    /** Identificador único del registro. */
    @Id
    private final Long id;

    /** ID del ConstructionType asociado (llave foránea). */
    private final Long constructionTypeId;

    /** ID del MaterialType asociado (llave foránea). */
    private final Long materialTypeId;

    /** Cantidad de material requerido por el tipo de construcción. */
    private final Double quantityRequired;

    /** Indicador de vigencia (soft delete). */
    private final Boolean active;

    /**
     * Constructor completo (obligatorio para entidades inmutables).
     */
    public ConstructionTypeMaterialEntity(
            Long id,
            Long constructionTypeId,
            Long materialTypeId,
            Double quantityRequired,
            Boolean active
    ) {
        this.id = id;
        this.constructionTypeId = constructionTypeId;
        this.materialTypeId = materialTypeId;
        this.quantityRequired = quantityRequired;
        this.active = active;
    }

    // ───────────────────────
    // GETTERS
    // ───────────────────────

    public Long getId() {
        return id;
    }

    public Long getConstructionTypeId() {
        return constructionTypeId;
    }

    public Long getMaterialTypeId() {
        return materialTypeId;
    }

    public Double getQuantityRequired() {
        return quantityRequired;
    }

    public Boolean getActive() {
        return active;
    }
}
