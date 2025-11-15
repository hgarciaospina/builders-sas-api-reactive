package com.builderssas.api.domain.model.constructionorder;

import com.builderssas.api.domain.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa una orden de construcción en el dominio.
 * Es un modelo inmutable cuya estructura coincide 1:1 con la entity
 * utilizada en la capa de persistencia R2DBC. Esta alineación directa
 * evita inconsistencias, facilita el mapeo y asegura que no existan
 * pérdidas de información entre capas.
 */
public record ConstructionOrderRecord(

        Long id,                        // Identificador único de la orden
        Long constructionRequestId,     // ID de la solicitud de construcción asociada
        Long projectId,                 // Proyecto al que pertenece la orden
        Long constructionTypeId,        // Tipo de construcción
        Long requestedByUserId,         // Usuario que creó la solicitud original
        Double latitude,                // Coordenada latitud
        Double longitude,               // Coordenada longitud
        LocalDate requestedDate,        // Fecha en que fue solicitada originalmente
        LocalDate scheduledStartDate,   // Fecha programada de inicio
        LocalDate scheduledEndDate,     // Fecha programada de finalización
        OrderStatus orderStatus,        // Estado actual de la orden
        LocalDateTime createdAt,        // Fecha y hora de creación de la orden
        LocalDateTime updatedAt,        // Fecha y hora de última actualización
        String observations,            // Observaciones asociadas
        Boolean active                  // Indicador lógico de actividad
) {}
