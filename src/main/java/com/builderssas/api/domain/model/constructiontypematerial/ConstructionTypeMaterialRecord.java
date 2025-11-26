package com.builderssas.api.domain.model.constructiontypematerial;

/**
 * Record de dominio que representa la relación entre un ConstructionType
 * y un MaterialType, indicando la cantidad requerida de ese material.
 *
 * Este record forma parte de la capa de Dominio dentro de la Arquitectura Hexagonal.
 * Es 100% inmutable y no contiene lógica de negocio.
 *
 * Campos:
 * - id: Identificador único del registro.
 * - constructionTypeId: ID del tipo de construcción asociado.
 * - materialTypeId: ID del tipo de material asociado.
 * - quantityRequired: Cantidad requerida del material.
 * - active: Indicador de soft-delete.
 */
public record ConstructionTypeMaterialRecord(
        Long id,
        Long constructionTypeId,
        Long materialTypeId,
        Double quantityRequired,
        Boolean active
) {}
