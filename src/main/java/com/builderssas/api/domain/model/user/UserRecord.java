package com.builderssas.api.domain.model.user;

/**
 * Record de dominio que representa un usuario dentro del sistema.
 * Inmutable y sin lógica de negocio, siguiendo la Arquitectura Hexagonal.
 */
public record UserRecord(
        Long id,
        String username,
        String displayName,
        String email,
        Boolean active
) {}
