package com.builderssas.api.domain.model.constructiontype;

/**
 * Record de dominio que representa un Tipo de Construcción.
 *
 * Este record forma parte del Modelo de Dominio dentro de la
 * Arquitectura Hexagonal. Se utiliza para transportar información
 * inmutable entre las capas de aplicación, dominio e infraestructura.
 *
 * Atributos:
 *  • id            - Identificador único del tipo de construcción.
 *  • name          - Nombre descriptivo del tipo de construcción.
 *  • estimatedDays - Duración estimada del proceso constructivo.
 *  • active        - Indicador lógico que representa si el tipo
 *                    está habilitado o no en el catálogo.
 *
 * Este objeto:
 *  • Es completamente inmutable.
 *  • No contiene lógica de negocio.
 *  • Garantiza claridad y seguridad en el transporte de datos.
 */
public record ConstructionTypeRecord(
        Long id,
        String name,
        Integer estimatedDays,
        Boolean active
) {}
