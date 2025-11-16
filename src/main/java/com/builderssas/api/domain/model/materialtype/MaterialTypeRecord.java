package com.builderssas.api.domain.model.materialtype;

/**
 * Record de dominio que representa un Tipo de Material.
 *
 * Este objeto forma parte del Modelo de Dominio en la Arquitectura Hexagonal
 * y su propósito es transportar datos de forma **inmutable**, clara y segura
 * entre las diferentes capas del sistema.
 *
 * Características:
 *  • No contiene lógica de negocio.
 *  • Es completamente inmutable.
 *  • Corresponde 1:1 con la entity persistida vía R2DBC.
 *
 * Atributos:
 *  • id             - Identificador único del tipo de material.
 *  • name           - Nombre descriptivo del material.
 *  • unitOfMeasure  - Unidad de medida (kg, m³, unidades, etc.).
 *  • active         - Indicador lógico que determina si el material está habilitado.
 *
 * Este record se usa en:
 *  • Casos de uso reactivos
 *  • Adaptadores R2DBC
 *  • Capa de aplicación
 *  • Validaciones
 */
public record MaterialTypeRecord(
        Long id,
        String name,
        String unitOfMeasure,
        Boolean active
) {}
