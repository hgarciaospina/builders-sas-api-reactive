package com.builderssas.api.domain.model.constructionorder;

import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Factory funcional encargada de construir ordenes de construcción
 * a partir de una solicitud válida dentro del dominio.
 *
 * Esta clase concentra TODAS las reglas de creación de órdenes:
 *  • Fechas programadas
 *  • Estado inicial
 *  • Auditoría (createdAt / updatedAt)
 *  • Lógica de correspondencia entre solicitud y orden
 *
 * No contiene mutación de estado, no tiene setters,
 * y no usa ningún tipo de programación imperativa.
 */
public final class ConstructionOrderFactory {

    private ConstructionOrderFactory() {
        // Evita instanciación
    }

    /**
     * Construye un nuevo ConstructionOrderRecord usando las reglas
     * de negocio establecidas en el dominio.
     *
     * @param req solicitud ya validada y persistida
     * @return orden de construcción inmutable basada en la solicitud
     */
    public static ConstructionOrderRecord fromRequest(ConstructionRequestRecord req) {

        LocalDate scheduledStart = req.requestDate().plusDays(1);
        LocalDate scheduledEnd   = scheduledStart.plusDays(5);
        LocalDateTime now        = LocalDateTime.now();

        return new ConstructionOrderRecord(
                null,                        // id generado por BD
                req.id(),
                req.projectId(),
                req.constructionTypeId(),
                req.requestedByUserId(),
                req.latitude(),
                req.longitude(),
                req.requestDate(),
                scheduledStart,
                scheduledEnd,
                OrderStatus.PENDING,
                now,
                now,
                req.observations(),
                req.active()
        );
    }
}
