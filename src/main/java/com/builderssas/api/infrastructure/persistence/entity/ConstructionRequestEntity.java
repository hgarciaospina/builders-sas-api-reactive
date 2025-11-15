package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("construction_requests")
public class ConstructionRequestEntity {

    @Id
    private Long id;

    @Column("project_id")
    private Long projectId;

    @Column("construction_type_id")
    private Long constructionTypeId;

    private Double latitude;

    private Double longitude;

    @Column("requested_by_user_id")
    private Long requestedByUserId;

    @Column("request_date")
    private LocalDate requestDate;

    @Column("request_status")
    private String requestStatus;

    private String observations;

    /**
     * Campo adicional para eliminación lógica o futuros flags.
     */
    private boolean active;

    // -------------------
    // GETTERS & SETTERS
    // -------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getConstructionTypeId() { return constructionTypeId; }
    public void setConstructionTypeId(Long constructionTypeId) { this.constructionTypeId = constructionTypeId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Long getRequestedByUserId() { return requestedByUserId; }
    public void setRequestedByUserId(Long requestedByUserId) { this.requestedByUserId = requestedByUserId; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    public String getRequestStatus() { return requestStatus; }
    public void setRequestStatus(String requestStatus) { this.requestStatus = requestStatus; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
