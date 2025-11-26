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
 * Esta clase pertenece exclusivamente a la capa de infraestructura.
 * Su responsabilidad es reflejar la estructura de la tabla
 * inventory_movements en la base de datos.
 *
 * El dominio trabaja con el record inmutable InventoryMovement.
 * Aquí no existe lógica de negocio, validaciones ni mutaciones.
 *
 * Arquitectura utilizada: Hexagonal.
 * Tecnología utilizada: Spring WebFlux + R2DBC.
 */
@Table("inventory_movements")
public final class InventoryMovementEntity {

    @Id
    private final Long id;

    @Column("material_id")
    private final Long materialId;

    @Column("material_name")
    private final String materialName;

    @Column("unit_of_measure")
    private final String unitOfMeasure;

    @Column("movement_type")
    private final InventoryMovementType movementType;

    @Column("quantity")
    private final Double quantity;

    @Column("previous_stock")
    private final Double previousStock;

    @Column("final_stock")
    private final Double finalStock;

    @Column("movement_date")
    private final LocalDateTime movementDate;

    @Column("order_id")
    private final Long orderId;

    @Column("reason")
    private final InventoryReason reason;

    @Column("status")
    private final InventoryStatus status;

    @Column("user_id")
    private final Long userId;

    @Column("user_full_name")
    private final String userFullName;

    @Column("user_role")
    private final String userRole;

    /**
     * Constructor completo e inmutable requerido por R2DBC.
     * No existe constructor vacío, ya que esta entidad debe
     * mantenerse inmutable y R2DBC es capaz de utilizar este
     * constructor para materializar resultados.
     */
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
