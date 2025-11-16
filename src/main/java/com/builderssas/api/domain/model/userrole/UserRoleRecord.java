package com.builderssas.api.domain.model.userrole;

import java.time.LocalDateTime;

/**
 * Record de dominio que representa una asignación entre un usuario y un rol.
 *
 * Forma parte del Modelo de Dominio dentro de la Arquitectura Hexagonal.
 * Se utiliza para transportar información inmutable entre las capas de
 * dominio, aplicación e infraestructura.
 *
 * Este modelo incorpora trazabilidad empresarial completa mediante:
 *  - assignedAt : Momento exacto en que se asignó el rol al usuario.
 *  - createdAt  : Momento en que se creó el registro en persistencia.
 *  - updatedAt  : Momento en que el registro fue modificado por última vez.
 *
 * También incorpora el campo active para soportar "soft delete",
 * permitiendo mantener historial sin eliminaciones físicas.
 *
 * Este objeto:
 *  - Es completamente inmutable.
 *  - No contiene lógica de negocio.
 *  - Refleja de forma coherente la estructura de la entidad persistente.
 *  - Sirve como contrato estable entre dominio, aplicación e infraestructura.
 */
public record UserRoleRecord(
        Long id,
        Long userId,
        Long roleId,
        LocalDateTime assignedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean active
) {}
