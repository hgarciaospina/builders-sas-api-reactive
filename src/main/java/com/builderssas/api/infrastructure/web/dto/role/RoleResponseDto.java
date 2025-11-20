package com.builderssas.api.infrastructure.web.dto.role;

/**
 * DTO utilizado para exponer información de un rol hacia el cliente.
 *
 * Este DTO:
 * - Es inmutable.
 * - No contiene lógica de negocio.
 * - No incluye campos internos o sensibles.
 * - Representa únicamente la información pública del RoleRecord.
 */
public record RoleResponseDto(

        Long id,
        String name,
        String description,
        Boolean active

) {}
