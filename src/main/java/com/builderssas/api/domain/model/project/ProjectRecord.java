package com.builderssas.api.domain.model.project;

/**
 * Record de dominio que representa un Proyecto dentro del sistema.
 *
 * Este objeto forma parte del Modelo de Dominio en la Arquitectura Hexagonal
 * y su propósito es transportar datos de manera **inmutable**, clara y segura
 * entre las capas del sistema sin introducir lógica de negocio.
 *
 * Características:
 *  • Es totalmente inmutable.
 *  • No contiene comportamiento ni reglas.
 *  • Su estructura es equivalente 1:1 con la entity persistida vía R2DBC.
 *
 * Atributos:
 *  • id     - Identificador único del proyecto.
 *  • name   - Nombre descriptivo del proyecto.
 *  • code   - Código único del proyecto.
 *  • active - Indicador lógico que determina si el proyecto está habilitado.
 *
 * Este record se utiliza en:
 *  • Casos de uso de la capa de aplicación.
 *  • Adaptadores R2DBC.
 *  • Transporte seguro de información entre capas.
 */
public record ProjectRecord(
        Long id,
        String name,
        String code,
        Boolean active
) {}
