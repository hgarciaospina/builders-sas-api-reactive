package com.builderssas.api.infrastructure.persistence.entity;

import com.builderssas.api.domain.model.enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad persistente inmutable que representa una orden de construcción.
 *
 * NO CONTIENE lógica.
 * NO ASIGNA valores por defecto.
 * NO modifica estados.
 * NO cambia enums.
 * Solo representa la fila tal como está en la BD.
 */
@Table("construction_orders")
public class ConstructionOrderEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("construction_request_id")
    private final Long constructionRequestId;

    @Column("project_id")
    private final Long projectId;

    @Column("construction_type_id")
    private final Long constructionTypeId;

    @Column("requested_by_user_id")
    private final Long requestedByUserId;

    @Column("latitude")
    private final Double latitude;

    @Column("longitude")
    private final Double longitude;

    @Column("requested_date")
    private final LocalDate requestedDate;

    @Column("scheduled_start_date")
    private final LocalDate scheduledStartDate;

    @Column("scheduled_end_date")
    private final LocalDate scheduledEndDate;

    @Column("order_status")
    private final OrderStatus orderStatus;

    @Column("created_at")
    private final LocalDateTime createdAt;

    @Column("updated_at")
    private final LocalDateTime updatedAt;

    @Column("observations")
    private final String observations;

    @Column("active")
    private final Boolean active;

    /**
     * Constructor completo usado por R2DBC para mapear columnas.
     * NO agrega lógica. NO pone defaults.
     * R2DBC lo usa gracias a que incluye todos los campos.
     */
    public ConstructionOrderEntity(
            Long id,
            Long constructionRequestId,
            Long projectId,
            Long constructionTypeId,
            Long requestedByUserId,
            Double latitude,
            Double longitude,
            LocalDate requestedDate,
            LocalDate scheduledStartDate,
            LocalDate scheduledEndDate,
            OrderStatus orderStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String observations,
            Boolean active
    ) {
        this.id = id;
        this.constructionRequestId = constructionRequestId;
        this.projectId = projectId;
        this.constructionTypeId = constructionTypeId;
        this.requestedByUserId = requestedByUserId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestedDate = requestedDate;
        this.scheduledStartDate = scheduledStartDate;
        this.scheduledEndDate = scheduledEndDate;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.observations = observations;
        this.active = active;
    }

    // ========================================
    // GETTERS (INMUTABLE — SIN SETTERS)
    // ========================================

    public Long getId() { return id; }
    public Long getConstructionRequestId() { return constructionRequestId; }
    public Long getProjectId() { return projectId; }
    public Long getConstructionTypeId() { return constructionTypeId; }
    public Long getRequestedByUserId() { return requestedByUserId; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public LocalDate getRequestedDate() { return requestedDate; }
    public LocalDate getScheduledStartDate() { return scheduledStartDate; }
    public LocalDate getScheduledEndDate() { return scheduledEndDate; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getObservations() { return observations; }
    public Boolean getActive() { return active; }
}
