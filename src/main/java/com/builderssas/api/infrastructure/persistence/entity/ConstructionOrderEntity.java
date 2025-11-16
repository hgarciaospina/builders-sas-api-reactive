package com.builderssas.api.infrastructure.persistence.entity;

import com.builderssas.api.domain.model.enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad persistente que representa una orden de construcción.
 *
 * Su misión es mapear directamente la tabla "construction_orders"
 * dentro de la base de datos utilizando R2DBC.
 *
 * No contiene lógica de negocio. Es un contenedor de datos para
 * la capa de infraestructura y las operaciones reactivas.
 */
@Table("construction_orders")
public class ConstructionOrderEntity {

    /** Identificador único de la orden. */
    @Id
    @Column("id")
    private Long id;

    /** ID de la solicitud de construcción asociada. */
    @Column("construction_request_id")
    private Long constructionRequestId;

    /** ID del proyecto al que pertenece esta orden. */
    @Column("project_id")
    private Long projectId;

    /** ID del tipo de construcción utilizado. */
    @Column("construction_type_id")
    private Long constructionTypeId;

    /** ID del usuario que solicitó originalmente esta orden. */
    @Column("requested_by_user_id")
    private Long requestedByUserId;

    /** Latitud de la ubicación de la construcción. */
    @Column("latitude")
    private Double latitude;

    /** Longitud de la ubicación de la construcción. */
    @Column("longitude")
    private Double longitude;

    /** Fecha en que se creó la solicitud original. */
    @Column("requested_date")
    private LocalDate requestedDate;

    /** Fecha programada para el inicio de la construcción. */
    @Column("scheduled_start_date")
    private LocalDate scheduledStartDate;

    /** Fecha programada de finalización. */
    @Column("scheduled_end_date")
    private LocalDate scheduledEndDate;

    /** Estado actual de la orden. */
    @Column("order_status")
    private OrderStatus orderStatus;

    /** Fecha de creación del registro. */
    @Column("created_at")
    private LocalDateTime createdAt;

    /** Fecha de última actualización del registro. */
    @Column("updated_at")
    private LocalDateTime updatedAt;

    /** Observaciones generadas por el sistema (consumos, cálculos, etc). */
    @Column("observations")
    private String observations;

    /** Indicador de si la orden está activa. */
    @Column("active")
    private Boolean active;

    // ============================================================================================
    // CONSTRUCTORES
    // ============================================================================================

    /** Constructor vacío requerido por R2DBC. */
    public ConstructionOrderEntity() {}

    /**
     * Constructor completo utilizado por mappers funcionales.
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

    // ============================================================================================
    // GETTERS & SETTERS
    // ============================================================================================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getConstructionRequestId() { return constructionRequestId; }
    public void setConstructionRequestId(Long constructionRequestId) { this.constructionRequestId = constructionRequestId; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getConstructionTypeId() { return constructionTypeId; }
    public void setConstructionTypeId(Long constructionTypeId) { this.constructionTypeId = constructionTypeId; }

    public Long getRequestedByUserId() { return requestedByUserId; }
    public void setRequestedByUserId(Long requestedByUserId) { this.requestedByUserId = requestedByUserId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDate getRequestedDate() { return requestedDate; }
    public void setRequestedDate(LocalDate requestedDate) { this.requestedDate = requestedDate; }

    public LocalDate getScheduledStartDate() { return scheduledStartDate; }
    public void setScheduledStartDate(LocalDate scheduledStartDate) { this.scheduledStartDate = scheduledStartDate; }

    public LocalDate getScheduledEndDate() { return scheduledEndDate; }
    public void setScheduledEndDate(LocalDate scheduledEndDate) { this.scheduledEndDate = scheduledEndDate; }

    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
