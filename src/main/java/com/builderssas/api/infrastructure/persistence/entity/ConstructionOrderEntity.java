package com.builderssas.api.infrastructure.persistence.entity;

import com.builderssas.api.domain.model.enums.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity R2DBC que representa la tabla `construction_orders`.
 * Se utiliza exclusivamente en la capa de persistencia para mapear
 * columnas de la base de datos a campos simples (sin relaciones).
 *
 * Esta clase no contiene lógica de negocio; su función es ser un
 * contenedor de datos para las operaciones reactivas de lectura y escritura.
 */
@Table("construction_orders")
public class ConstructionOrderEntity {

    @Id
    private Long id;

    @Column("construction_request_id")
    private Long constructionRequestId;

    @Column("project_id")
    private Long projectId;

    @Column("construction_type_id")
    private Long constructionTypeId;

    @Column("requested_by_user_id")
    private Long requestedByUserId;

    private Double latitude;
    private Double longitude;

    @Column("requested_date")
    private LocalDate requestedDate;

    @Column("scheduled_start_date")
    private LocalDate scheduledStartDate;

    @Column("scheduled_end_date")
    private LocalDate scheduledEndDate;

    @Column("order_status")
    private OrderStatus orderStatus;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    private String observations;

    private Boolean active;

    // ---------------------------------------------------------
    //  Constructor vacío (NECESARIO para R2DBC)
    // ---------------------------------------------------------
    public ConstructionOrderEntity() {
    }

    // ---------------------------------------------------------
    //  Constructor completo (usado por el mapper funcional)
    // ---------------------------------------------------------
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

    // ---------------------------------------------------------
    //  Getters y Setters (NO se eliminan ni cambian)
    // ---------------------------------------------------------

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
