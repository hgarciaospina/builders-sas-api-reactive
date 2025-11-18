package com.builderssas.api.infrastructure.web.dto.constructionorder;

import com.builderssas.api.domain.model.enums.OrderStatus;

import java.time.LocalDate;

/**
 * DTO utilizado para recibir las solicitudes de creación
 * de órdenes de construcción desde la capa web.
 *
 * No contiene lógica de negocio. Es un simple portador de datos.
 */
public record CreateConstructionOrderRequestDto(
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
        String observations,
        Boolean active
) {}
