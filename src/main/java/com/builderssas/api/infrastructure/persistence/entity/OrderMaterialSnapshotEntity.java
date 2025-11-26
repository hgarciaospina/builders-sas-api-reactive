package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente que representa el snapshot del consumo de materiales
 * al momento de la creación de una orden de construcción.
 *
 * Esta entidad es INMUTABLE y conserva un registro histórico exacto.
 */
@Table("order_material_snapshots")
public final class OrderMaterialSnapshotEntity {

    /** Identificador único del snapshot. */
    @Id
    @Column("id")
    private final Long id;

    /** ID de la orden asociada. */
    @Column("order_id")
    private final Long orderId;

    /** ID del material congelado. */
    @Column("material_id")
    private final Long materialId;

    /** Nombre del material en el momento de la orden. */
    @Column("material_name")
    private final String materialName;

    /** Unidad de medida del material. */
    @Column("unit_of_measure")
    private final String unitOfMeasure;

    /** Stock antes del consumo. */
    @Column("stock_before")
    private final Double stockBefore;

    /** Cantidad requerida por la orden. */
    @Column("required_quantity")
    private final Double requiredQuantity;

    /** Stock después del consumo. */
    @Column("stock_after")
    private final Double stockAfter;

    /** Fecha y hora exacta del snapshot. */
    @Column("snapshot_date")
    private final LocalDateTime snapshotDate;

    /** Fecha de creación del registro. */
    @Column("created_at")
    private final LocalDateTime createdAt;

    /** Fecha de última actualización del registro. Normalmente null porque no se actualiza. */
    @Column("updated_at")
    private final LocalDateTime updatedAt;

    // ============================================================================================
    // CONSTRUCTOR INMUTABLE
    // ============================================================================================

    public OrderMaterialSnapshotEntity(
            Long id,
            Long orderId,
            Long materialId,
            String materialName,
            String unitOfMeasure,
            Double stockBefore,
            Double requiredQuantity,
            Double stockAfter,
            LocalDateTime snapshotDate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.materialId = materialId;
        this.materialName = materialName;
        this.unitOfMeasure = unitOfMeasure;
        this.stockBefore = stockBefore;
        this.requiredQuantity = requiredQuantity;
        this.stockAfter = stockAfter;
        this.snapshotDate = snapshotDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ============================================================================================
    // GETTERS
    // ============================================================================================

    public Long getId() { return id; }

    public Long getOrderId() { return orderId; }

    public Long getMaterialId() { return materialId; }

    public String getMaterialName() { return materialName; }

    public String getUnitOfMeasure() { return unitOfMeasure; }

    public Double getStockBefore() { return stockBefore; }

    public Double getRequiredQuantity() { return requiredQuantity; }

    public Double getStockAfter() { return stockAfter; }

    public LocalDateTime getSnapshotDate() { return snapshotDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
