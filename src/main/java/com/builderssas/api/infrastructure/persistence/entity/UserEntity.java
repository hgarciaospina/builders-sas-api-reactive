package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad persistente e inmutable que representa un usuario dentro del sistema.
 *
 * Esta clase refleja exactamente la estructura de la tabla "users"
 * en la base de datos. No contiene lógica de negocio, setters,
 * mutaciones ni constructores vacíos, cumpliendo las reglas del
 * proyecto Builders-SAS de mantener infraestructura totalmente
 * inmutable.
 *
 * Su única responsabilidad es transportar datos hacia/desde la
 * capa de persistencia utilizando R2DBC.
 */
@Table("users")
public final class UserEntity {

    /** Identificador único del usuario. */
    @Id
    @Column("id")
    private final Long id;

    /** Nombre de usuario utilizado internamente para autenticación. */
    @Column("username")
    private final String username;

    /** Nombre visible del usuario en la aplicación. */
    @Column("display_name")
    private final String displayName;

    /** Correo electrónico del usuario. */
    @Column("email")
    private final String email;

    /** Indicador lógico del estado del usuario. */
    @Column("active")
    private final Boolean active;

    /**
     * Constructor completo para creación inmutable de la entidad.
     */
    public UserEntity(
            Long id,
            String username,
            String displayName,
            String email,
            Boolean active
    ) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.active = active;
    }

    // Getters inmutables — requeridos por Spring Data R2DBC
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return active;
    }
}
