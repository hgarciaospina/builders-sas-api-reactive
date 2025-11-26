package com.builderssas.api.infrastructure.persistence.entity;

import com.builderssas.api.domain.model.enums.ProjectStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad persistente que representa un proyecto dentro del sistema.
 *
 * - Inmutable
 * - Compatible con R2DBC
 * - Representa directamente la tabla "projects"
 * - Ningún setter
 * - Todos los campos necesarios según reglas actuales
 */
@Table("projects")
public final class ProjectEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("name")
    private final String name;

    @Column("code")
    private final String code;

    @Column("description")
    private final String description;

    @Column("start_date")
    private final LocalDate startDate;

    @Column("end_date")
    private final LocalDateTime endDate;

    @Column("progress_percentage")
    private final Integer progressPercentage;

    @Column("status")
    private final ProjectStatus status;

    @Column("observations")
    private final String observations;

    @Column("created_by_user_id")
    private final Long createdByUserId;

    @Column("active")
    private final Boolean active;

    // ================================
    // CAMPOS QUE PEDISTE AGREGAR
    // ================================

    @Column("created_at")
    private final LocalDateTime createdAt;

    @Column("updated_at")
    private final LocalDateTime updatedAt;

    /**
     * Constructor completo para R2DBC.
     */
    public ProjectEntity(
            Long id,
            String name,
            String code,
            String description,
            LocalDate startDate,
            LocalDateTime endDate,
            Integer progressPercentage,
            ProjectStatus status,
            String observations,
            Long createdByUserId,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressPercentage = progressPercentage;
        this.status = status;
        this.observations = observations;
        this.createdByUserId = createdByUserId;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ======================
    // GETTERS (sin setters)
    // ======================

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getCode() { return code; }

    public String getDescription() { return description; }

    public LocalDate getStartDate() { return startDate; }

    public LocalDateTime getEndDate() { return endDate; }

    public Integer getProgressPercentage() { return progressPercentage; }

    public ProjectStatus getStatus() { return status; }

    public String getObservations() { return observations; }

    public Long getCreatedByUserId() { return createdByUserId; }

    public Boolean getActive() { return active; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
