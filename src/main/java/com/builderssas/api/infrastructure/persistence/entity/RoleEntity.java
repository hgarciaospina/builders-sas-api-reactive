    package com.builderssas.api.infrastructure.persistence.entity;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.relational.core.mapping.Table;

    @Table("roles")
    public class RoleEntity {

        private Long id;
private String name;
private String description;
private boolean active;

        public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }
public boolean getActive() { return active; }
public void setActive(boolean active) { this.active = active; }
    }
