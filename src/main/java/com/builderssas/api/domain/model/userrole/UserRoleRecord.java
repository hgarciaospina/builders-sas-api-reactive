// File: src/main/java/com/builderssas/api/domain/model/userrole/UserRoleRecord.java
package com.builderssas.api.domain.model.userrole;

import java.time.LocalDateTime;

/**
 * Record de dominio que representa la relación entre un usuario y un rol.
 *
 * Forma parte del Modelo de Dominio dentro de la Arquitectura Hexagonal
 * del proyecto Builders-SAS. Es una estructura inmutable utilizada para
 * transportar los datos de la asignación usuario-rol sin exponer detalles
 * de la capa de infraestructura ni dependencias técnicas.
 *
 * Este record refleja exactamente los campos manejados en la tabla
 * "user_roles" y es utilizado por los casos de uso, puertos y lógica
 * de negocio.
 *
 * Campos:
 * - id          : Identificador único de la relación.
 * - userId      : Identificador del usuario.
 * - roleId      : Identificador del rol asignado.
 * - assignedAt  : Momento en que se asignó el rol.
 * - createdAt   : Fecha de creación del registro.
 * - updatedAt   : Fecha de última actualización del registro.
 * - active      : Estado lógico que indica si la relación está vigente.
 */
public record UserRoleRecord(
        Long id,
        Long userId,
        Long roleId,
        LocalDateTime assignedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean active
) {
}
