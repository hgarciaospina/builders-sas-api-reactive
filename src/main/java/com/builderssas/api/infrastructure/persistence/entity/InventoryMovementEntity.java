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
    private Long id;

    /** ID del material al que se aplicó el movimiento. */
    @Column("material_id")
    private Long materialId;

    /** Nombre del material (congelado). */
    @Column("material_name")
    private String materialName;

    /** Unidad de medida (congelada). */
    @Column("unit_of_measure")
    private String unitOfMeasure;

    /** Dirección del movimiento (IN/OUT). */
    @Column("movement_type")
    private InventoryMovementType movementType;

    /** Cantidad movida (siempre positiva). */
    @Column("quantity")
    private Double quantity;

    /** Stock antes del movimiento. */
    @Column("previous_stock")
    private Double previousStock;

    /** Stock después del movimiento. */
    @Column("final_stock")
    private Double finalStock;

    /** Fecha y hora en que ocurrió el movimiento. */
    @Column("movement_date")
    private LocalDateTime movementDate;

    /** ID de la orden relacionada (opcional). */
    @Column("order_id")
    private Long orderId;

    /** Motivo del movimiento. */
    @Column("reason")
    private InventoryReason reason;

    /** Estado del movimiento. */
    @Column("status")
    private InventoryStatus status;

    /** ID del usuario responsable del movimiento. */
    @Column("user_id")
    private Long userId;

    /** Nombre completo del usuario (congelado). */
    @Column("user_full_name")
    private String userFullName;

    /** Rol del usuario al momento del movimiento. */
    @Column("user_role")
    private String userRole;

    // ============================================================================================
    // GETTERS & SETTERS (Solo infraestructura; dominio sigue siendo inmutable)
    // ============================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public InventoryMovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(InventoryMovementType movementType) {
        this.movementType = movementType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPreviousStock() {
        return previousStock;
    }

    public void setPreviousStock(Double previousStock) {
        this.previousStock = previousStock;
    }

    public Double getFinalStock() {
        return finalStock;
    }

    public void setFinalStock(Double finalStock) {
        this.finalStock = finalStock;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public InventoryReason getReason() {
        return reason;
    }

    public void setReason(InventoryReason reason) {
        this.reason = reason;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
