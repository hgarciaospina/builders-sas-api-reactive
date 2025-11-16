package com.builderssas.api.domain.model.role;

/**
 * Record de dominio que representa un Rol dentro del sistema Builders-SAS.
 *
 * Este record forma parte del Modelo de Dominio bajo la Arquitectura Hexagonal
 * y se utiliza como DTO inmutable para transportar información entre
 * la capa de aplicación, dominio e infraestructura.
 *
 * Atributos:
 *  • id          – Identificador único del rol.
 *  • name        – Nombre interno del rol (ej.: "ROLE_ADMIN").
 *  • description – Descripción del propósito del rol.
 *  • active      – Indicador lógico de disponibilidad del rol en el sistema.
 *
 * Características:
 *  • Inmutable por diseño.
 *  • Sin lógica de negocio.
 *  • Favorece claridad en transporte de datos y consistencia del dominio.
 */
public record RoleRecord(
        Long id,
        String name,
        String description,
        Boolean active
) {}
