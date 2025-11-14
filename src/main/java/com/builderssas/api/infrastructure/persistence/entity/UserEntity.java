    package com.builderssas.api.infrastructure.persistence.entity;

    import org.springframework.data.annotation.Id;
    import org.springframework.data.relational.core.mapping.Table;

    @Table("users")
    public class UserEntity {

        private Long id;
private String username;
private String displayName;
private String email;
private boolean active;

        public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getUsername() { return username; }
public void setUsername(String username) { this.username = username; }
public String getDisplayName() { return displayName; }
public void setDisplayName(String displayName) { this.displayName = displayName; }
public String getEmail() { return email; }
public void setEmail(String email) { this.email = email; }
public boolean getActive() { return active; }
public void setActive(boolean active) { this.active = active; }
    }
