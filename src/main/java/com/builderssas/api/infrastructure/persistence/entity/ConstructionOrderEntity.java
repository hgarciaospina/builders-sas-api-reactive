    package com.builderssas.api.infrastructure.persistence.entity;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.relational.core.mapping.Table;

    @Table("constructionorders")
    public class ConstructionOrderEntity {

        private Long id;
private Long requestId;
private String status;
private Integer dayCount;
private boolean active;

        public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public Long getRequestId() { return requestId; }
public void setRequestId(Long requestId) { this.requestId = requestId; }
public String getStatus() { return status; }
public void setStatus(String status) { this.status = status; }
public Integer getDayCount() { return dayCount; }
public void setDayCount(Integer dayCount) { this.dayCount = dayCount; }
public boolean getActive() { return active; }
public void setActive(boolean active) { this.active = active; }
    }
