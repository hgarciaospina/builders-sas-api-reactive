package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente que representa el snapshot de consumo de materiales
 * generado durante la creación de una orden de construcción.
 *
 * Esta entidad permite conservar un registro histórico INMUTABLE del estado
 * del inventario en el instante en que la orden fue creada.
 *
 * Beneficios:
 * - Auditoría confiable.
 * - Reportes consistentes.
 * - Independencia total del catálogo vivo de materiales.
 *
 * Cada fila refleja un material consumido por una orden específica.
 */
@Table("order_material_snapshots")
public class OrderMaterialSnapshotEntity {

    /** Identificador único del snapshot. */
    @Id
    private Long id;

    /** ID de la orden asociada. */
    @Column("order_id")
    private Long orderId;

    /** ID del material (congelado). */
    @Column("material_id")
    private Long materialId;

    /** Nombre del material en el momento de la orden. */
    @Column("material_name")
    private String materialName;

    /** Unidad de medida del material. */
    @Column("unit_of_measure")
    private String unitOfMeasure;

    /** Stock antes del consumo. */
    @Column("stock_before")
    private Double stockBefore;

    /** Cantidad requerida por la orden. */
    @Column("required_quantity")
    private Double requiredQuantity;

    /** Stock después del consumo. */
    @Column("stock_after")
    private Double stockAfter;

    /** Fecha y hora del snapshot. Normalmente coincide con la creación de la orden. */
    @Column("snapshot_date")
    private LocalDateTime snapshotDate;

    // ============================================================================================
    // GETTERS & SETTERS
    // ============================================================================================

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getMaterialId() { return materialId; }

    public void setMaterialId(Long materialId) { this.materialId = materialId; }

    public String getMaterialName() { return materialName; }

    public void setMaterialName(String materialName) { this.materialName = materialName; }

    public String getUnitOfMeasure() { return unitOfMeasure; }

    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }

    public Double getStockBefore() { return stockBefore; }

    public void setStockBefore(Double stockBefore) { this.stockBefore = stockBefore; }

    public Double getRequiredQuantity() { return requiredQuantity; }

    public void setRequiredQuantity(Double requiredQuantity) { this.requiredQuantity = requiredQuantity; }

    public Double getStockAfter() { return stockAfter; }

    public void setStockAfter(Double stockAfter) { this.stockAfter = stockAfter; }

    public LocalDateTime getSnapshotDate() { return snapshotDate; }

    public void setSnapshotDate(LocalDateTime snapshotDate) { this.snapshotDate = snapshotDate; }
}
