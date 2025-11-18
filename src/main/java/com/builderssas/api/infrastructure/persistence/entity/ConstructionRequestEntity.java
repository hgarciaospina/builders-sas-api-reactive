package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente que representa una solicitud de construcción.
 *
 * Esta clase pertenece a la capa de Infraestructura dentro de la Arquitectura Hexagonal.
 * Es inmutable para garantizar consistencia y evitar programación imperativa en adapters.
 *
 * Características:
 *  - Constructor vacío requerido por R2DBC.
 *  - Constructor completo utilizado desde la capa de aplicación.
 *  - Campos finales para evitar mutaciones.
 */
@Table("construction_requests")
public class ConstructionRequestEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("project_id")
    private final Long projectId;

    @Column("construction_type_id")
    private final Long constructionTypeId;

    @Column("latitude")
    private final Double latitude;

    @Column("longitude")
    private final Double longitude;

    @Column("requested_by_user_id")
    private final Long requestedByUserId;

    @Column("request_date")
    private final LocalDateTime requestDate;

    @Column("request_status")
    private final String requestStatus;

    @Column("observations")
    private final String observations;

    @Column("active")
    private final Boolean active;

    // ---------------------------------------------------------
    // Constructor vacío requerido por R2DBC
    // ---------------------------------------------------------
    public ConstructionRequestEntity() {
        this.id = null;
        this.projectId = null;
        this.constructionTypeId = null;
        this.latitude = null;
        this.longitude = null;
        this.requestedByUserId = null;
        this.requestDate = null;
        this.requestStatus = null;
        this.observations = null;
        this.active = null;
    }

    // ---------------------------------------------------------
    // Constructor completo (constructor de persistencia)
    // ---------------------------------------------------------
    @PersistenceCreator
    public ConstructionRequestEntity(
            Long id,
            Long projectId,
            Long constructionTypeId,
            Double latitude,
            Double longitude,
            Long requestedByUserId,
            LocalDateTime requestDate,
            String requestStatus,
            String observations,
            Boolean active
    ) {
        this.id = id;
        this.projectId = projectId;
        this.constructionTypeId = constructionTypeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestedByUserId = requestedByUserId;
        this.requestDate = requestDate;
        this.requestStatus = requestStatus;
        this.observations = observations;
        this.active = active;
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------
    public Long getId() {
        return id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getConstructionTypeId() {
        return constructionTypeId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getRequestedByUserId() {
        return requestedByUserId;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getObservations() {
        return observations;
    }

    public Boolean getActive() {
        return active;
    }
}
