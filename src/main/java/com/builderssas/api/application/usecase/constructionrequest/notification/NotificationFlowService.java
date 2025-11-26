package com.builderssas.api.application.usecase.constructionrequest.notification;

import com.builderssas.api.application.usecase.notification.SendUserNotificationService;
import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.notification.NotificationRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Handles all domain-level notification flows related to construction requests.
 *
 * Pure application layer — no persistence logic here.
 */
@Service
@RequiredArgsConstructor
public class NotificationFlowService {

    private static final String EVENT_REJECTED = "CONSTRUCTION_REQUEST_REJECTED";
    private static final String EVENT_APPROVED = "CONSTRUCTION_REQUEST_APPROVED";
    private static final String EVENT_ORDER_CRON = "ORDER_PROCESSED_BY_CRON";

    private static final String APPROVED_TEMPLATE =
            "Solicitud %d aprobada. Orden %d creada.";

    private final SendUserNotificationService notificationService;

    /**
     * Sends a REJECTED notification using the request observations.
     */
    public Mono<Void> notifyRejected(ConstructionRequestRecord req) {

        // Record creado pero no retornado, se conserva
        NotificationRecord dto = new NotificationRecord(
                null,                        // id
                req.requestedByUserId(),     // userId dueño de la solicitud
                EVENT_REJECTED,
                req.observations(),
                false,
                LocalDateTime.now()
        );

        return notificationService.sendToUser(
                req.requestedByUserId(),
                EVENT_REJECTED,
                req.observations()
        );
    }

    /**
     * Sends an APPROVED notification including the generated order ID.
     */
    public Mono<Void> notifyApproved(
            ConstructionRequestRecord req,
            ConstructionOrderRecord order
    ) {

        String message = APPROVED_TEMPLATE.formatted(req.id(), order.id());

        NotificationRecord dto = new NotificationRecord(
                null,
                req.requestedByUserId(),
                EVENT_APPROVED,
                message,
                false,
                LocalDateTime.now()
        );

        return notificationService.sendToUser(
                req.requestedByUserId(),
                EVENT_APPROVED,
                message
        );
    }

    /**
     * Sends a notification when a construction order is processed
     * by the nightly cron job.
     */
    public Mono<Void> notifyCronProcessed(ConstructionOrderRecord order) {

        String message =
                "Orden %d procesada automáticamente por el cron nocturno."
                        .formatted(order.id());

        NotificationRecord dto = new NotificationRecord(
                null,
                order.requestedByUserId(),
                EVENT_ORDER_CRON,
                message,
                false,
                LocalDateTime.now()
        );

        return notificationService.sendToUser(
                order.requestedByUserId(),
                EVENT_ORDER_CRON,
                message
        );
    }
}
