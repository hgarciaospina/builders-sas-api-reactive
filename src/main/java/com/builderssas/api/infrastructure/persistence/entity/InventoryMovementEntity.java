package com.builderssas.api.infrastructure.persistence.entity;

import com.builderssas.api.domain.model.enums.InventoryMovementType;
import com.builderssas.api.domain.model.enums.InventoryStatus;
import com.builderssas.api.domain.model.enums.InventoryReason;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

/**
 * Entidad persistente que representa un movimiento de inventario.
 *
 * Esta clase forma parte de la capa de infraestructura y su única
 * responsabilidad es reflejar la estructura en base de datos.
 *
 * El dominio utiliza InventoryMovement (record inmutable).
 * Esta entidad es simplemente la representación persistente.
 *
 * NO INCLUYE LÓGICA DE NEGOCIO.
 */
@Table("inventory_movements")
public class InventoryMovementEntity {

    /** Identificador único del movimiento. */
    @Id
    private final Long id;

    /** ID del material al que se aplicó el movimiento. */
    @Column("material_id")
    private final Long materialId;

    /** Nombre del material (congelado). */
    @Column("material_name")
    private final String materialName;

    /** Unidad de medida (congelada). */
    @Column("unit_of_measure")
    private final String unitOfMeasure;

    /** Dirección del movimiento (IN/OUT). */
    @Column("movement_type")
    private final InventoryMovementType movementType;

    /** Cantidad movida (siempre positiva). */
    @Column("quantity")
    private final Double quantity;

    /** Stock antes del movimiento. */
    @Column("previous_stock")
    private final Double previousStock;

    /** Stock después del movimiento. */
    @Column("final_stock")
    private final Double finalStock;

    /** Fecha y hora en que ocurrió el movimiento. */
    @Column("movement_date")
    private final LocalDateTime movementDate;

    /** ID de la orden relacionada (opcional). */
    @Column("order_id")
    private final Long orderId;

    /** Motivo del movimiento. */
    @Column("reason")
    private final InventoryReason reason;

    /** Estado del movimiento. */
    @Column("status")
    private final InventoryStatus status;

    /** ID del usuario responsable del movimiento. */
    @Column("user_id")
    private final Long userId;

    /** Nombre completo del usuario (congelado). */
    @Column("user_full_name")
    private final String userFullName;

    /** Rol del usuario al momento del movimiento. */
    @Column("user_role")
    private final String userRole;

    // ============================================================================================
    // CONSTRUCTOR VACÍO REQUERIDO POR R2DBC
    // ============================================================================================

    public InventoryMovementEntity() {
        this.id = null;
        this.materialId = null;
        this.materialName = null;
        this.unitOfMeasure = null;
        this.movementType = null;
        this.quantity = null;
        this.previousStock = null;
        this.finalStock = null;
        this.movementDate = null;
        this.orderId = null;
        this.reason = null;
        this.status = null;
        this.userId = null;
        this.userFullName = null;
        this.userRole = null;
    }

    // ============================================================================================
    // CONSTRUCTOR COMPLETO INMUTABLE
    // ============================================================================================

    public InventoryMovementEntity(
            Long id,
            Long materialId,
            String materialName,
            String unitOfMeasure,
            InventoryMovementType movementType,
            Double quantity,
            Double previousStock,
            Double finalStock,
            LocalDateTime movementDate,
            Long orderId,
            InventoryReason reason,
            InventoryStatus status,
            Long userId,
            String userFullName,
            String userRole
    ) {
        this.id = id;
        this.materialId = materialId;
        this.materialName = materialName;
        this.unitOfMeasure = unitOfMeasure;
        this.movementType = movementType;
        this.quantity = quantity;
        this.previousStock = previousStock;
        this.finalStock = finalStock;
        this.movementDate = movementDate;
        this.orderId = orderId;
        this.reason = reason;
        this.status = status;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userRole = userRole;
    }

    // ============================================================================================
    // GETTERS (SIN SETTERS — INMUTABLE)
    // ============================================================================================

    public Long getId() { return id; }
    public Long getMaterialId() { return materialId; }
    public String getMaterialName() { return materialName; }
    public String getUnitOfMeasure() { return unitOfMeasure; }
    public InventoryMovementType getMovementType() { return movementType; }
    public Double getQuantity() { return quantity; }
    public Double getPreviousStock() { return previousStock; }
    public Double getFinalStock() { return finalStock; }
    public LocalDateTime getMovementDate() { return movementDate; }
    public Long getOrderId() { return orderId; }
    public InventoryReason getReason() { return reason; }
    public InventoryStatus getStatus() { return status; }
    public Long getUserId() { return userId; }
    public String getUserFullName() { return userFullName; }
    public String getUserRole() { return userRole; }
}
