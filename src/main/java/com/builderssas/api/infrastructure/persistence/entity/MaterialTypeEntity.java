    package com.builderssas.api.infrastructure.persistence.entity;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.relational.core.mapping.Table;

    @Table("materialtypes")
    public class MaterialTypeEntity {

        private Long id;
private String name;
private String unit;
private boolean active;

        public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getUnit() { return unit; }
public void setUnit(String unit) { this.unit = unit; }
public boolean getActive() { return active; }
public void setActive(boolean active) { this.active = active; }
    }
