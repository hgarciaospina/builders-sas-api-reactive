    package com.builderssas.api.infrastructure.persistence.entity;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.relational.core.mapping.Table;

    @Table("constructionrequests")
    public class ConstructionRequestEntity {

        private Long id;
private Long projectId;
private Long constructionTypeId;
private Double latitude;
private Double longitude;
private String status;
private boolean active;

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
public String getStatus() { return status; }
public void setStatus(String status) { this.status = status; }
public boolean getActive() { return active; }
public void setActive(boolean active) { this.active = active; }
    }
