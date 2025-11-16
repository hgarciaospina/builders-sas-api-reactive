package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente que representa un usuario dentro del sistema.
 *
 * Su responsabilidad es reflejar la estructura de la tabla "users"
 * en la base de datos. No contiene lógica de negocio; únicamente
 * expone los campos necesarios para operaciones de lectura y escritura.
 */
@Table("users")
public class UserEntity {

    /** Identificador único del usuario. */
    @Id
    @Column("id")
    private Long id;

    /** Nombre de usuario utilizado para autenticación. */
    @Column("username")
    private String username;

    /** Nombre visible del usuario en la aplicación. */
    @Column("display_name")
    private String displayName;

    /** Correo electrónico del usuario. */
    @Column("email")
    private String email;

    /**
     * Indicador lógico de si el usuario está activo.
     * Declarado como Boolean para mantener consistencia
     * con el modelo de dominio y evitar problemas de nullability.
     */
    @Column("active")
    private Boolean active;

    // ================================================================================
    // GETTERS & SETTERS — estandarizados y consistentes con los records/adapters
    // ================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter estandarizado para el campo "active".
     * Obligatorio para que los adaptadores puedan mapear el valor.
     */
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
