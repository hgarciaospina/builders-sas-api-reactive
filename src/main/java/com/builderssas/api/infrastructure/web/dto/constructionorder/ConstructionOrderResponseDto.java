package com.builderssas.api.infrastructure.web.dto.constructionorder;

import com.builderssas.api.domain.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO utilizado para exponer información de una orden de construcción
 * hacia el cliente. Este DTO se construye a partir del record del dominio.
 *
 * No contiene lógica y no incluye datos internos sensibles. Su propósito
 * es representar campos relevantes que el cliente necesita visualizar.
 */
public record ConstructionOrderResponseDto(

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

) {}
