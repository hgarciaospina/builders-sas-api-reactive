package com.builderssas.api.domain.model.materialtype;

/**
 * Record de dominio que representa un Tipo de Material.
 *
 * Este objeto forma parte del Modelo de Dominio en la Arquitectura Hexagonal.
 * Es completamente inmutable y solo transporta datos entre capas.
 *
 * Atributos:
 *  • id             - Identificador único del tipo de material.
 *  • code           - Código corto (CE, GR, AR...) que identifica el material.
 *  • name           - Nombre del material.
 *  • unitOfMeasure  - Unidad de medida (kg, m³, unidades, etc.).
 *  • active         - Indica si está habilitado en el sistema.
 *
 * No contiene lógica de negocio.
 */
public record MaterialTypeRecord(
        Long id,
        String code,
        String name,
        String unitOfMeasure,
        Boolean active
) {}
