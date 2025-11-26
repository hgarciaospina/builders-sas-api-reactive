package com.builderssas.api.domain.model.role;

/**
 * Record de dominio que representa un Rol dentro del sistema.
 *
 * Este record es inmutable y forma parte del Modelo de Dominio
 * según la Arquitectura Hexagonal aplicada en el proyecto.
 *
 * Solo define la estructura de datos que el dominio manipula.
 * No contiene lógica de negocio, validaciones ni mapeos, que
 * corresponden a sus respectivas capas.
 */
public record RoleRecord(
        Long id,
        String name,
        String description,
        Boolean active
) { }
