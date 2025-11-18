package com.builderssas.api.domain.model.constructionorder;

import com.builderssas.api.domain.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Record de dominio que representa una Orden de Construcción.
 *
 * Este objeto forma parte del Modelo de Dominio bajo la Arquitectura
 * Hexagonal y se utiliza para transportar datos de manera **inmutable**
 * entre las capas de aplicación y persistencia.
 *
 * Características clave:
 *  • Forma 1:1 con la entity R2DBC correspondiente.
 *  • No contiene lógica de negocio.
 *  • Garantiza integridad y trazabilidad de los valores asociados
 *    a la orden.
 *
 * Atributos:
 *  • id                      - Identificador único de la orden.
 *  • constructionRequestId   - ID de la solicitud que origina la orden.
 *  • projectId               - Proyecto al cual pertenece la orden.
 *  • constructionTypeId      - Tipo de construcción aplicada.
 *  • requestedByUserId       - Usuario que hizo la solicitud original.
 *  • latitude / longitude    - Coordenadas de ejecución.
 *  • requestedDate           - Fecha en que fue realizada la solicitud.
 *  • scheduledStartDate      - Fecha programada de inicio de la obra.
 *  • scheduledEndDate        - Fecha programada de finalización.
 *  • orderStatus             - Estado operacional de la orden.
 *  • createdAt               - Fecha/hora de creación del registro.
 *  • updatedAt               - Fecha/hora de última actualización.
 *  • observations            - Información o notas relevantes del sistema.
 *  • active                  - Indicador booleano de disponibilidad.
 *
 * Este record se usa ampliamente para:
 *  • Casos de uso reactivos (WebFlux)
 *  • Persistencia R2DBC
 *  • Mapeos limpios entre capas
 *  • Auditoría y consultas empresariales
 */
public record ConstructionOrderRecord(

        Long id,
        Long constructionRequestId,
        Long projectId,
        Long constructionTypeId,
        Long requestedByUserId,
        Double latitude,
        Double longitude,
        LocalDate requestedDate,
        LocalDate scheduledStartDate,
        LocalDate scheduledEndDate,
        OrderStatus orderStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String observations,
        Boolean active
) {

    /**
     * Crea un nuevo record copiando todos los valores,
     * excepto el campo updatedAt, que se reemplaza.
     *
     * Mantiene la inmutabilidad total y no altera ningún otro dato.
     */
    public ConstructionOrderRecord withUpdatedAt(LocalDateTime newUpdatedAt) {
        return new ConstructionOrderRecord(
                id,
                constructionRequestId,
                projectId,
                constructionTypeId,
                requestedByUserId,
                latitude,
                longitude,
                requestedDate,
                scheduledStartDate,
                scheduledEndDate,
                orderStatus,
                createdAt,
                newUpdatedAt,
                observations,
                active
        );
    }

    /**
     * Crea un nuevo record actualizando únicamente el estado (orderStatus),
     * conservando el resto de atributos exactamente igual.
     */
    public ConstructionOrderRecord withStatus(OrderStatus newStatus) {
        return new ConstructionOrderRecord(
                id,
                constructionRequestId,
                projectId,
                constructionTypeId,
                requestedByUserId,
                latitude,
                longitude,
                requestedDate,
                scheduledStartDate,
                scheduledEndDate,
                newStatus,
                createdAt,
                updatedAt,
                observations,
                active
        );
    }
}
