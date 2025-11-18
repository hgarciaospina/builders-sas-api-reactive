package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
import com.builderssas.api.domain.port.in.constructionorder.CreateConstructionOrderUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Caso de uso encargado de crear Órdenes de Construcción manualmente
 * a través del endpoint público (POST /construction-orders).
 *
 * Características:
 *  • Pertenece estrictamente a la capa de aplicación.
 *  • No tiene lógica de infraestructura.
 *  • Asegura valores críticos:
 *      - createdAt
 *      - updatedAt
 *      - orderStatus
 *
 *  • Mantiene la arquitectura 100% funcional e inmutable.
 *  • Nunca modifica records ya creados.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionOrderService implements CreateConstructionOrderUseCase {

    private final ConstructionOrderRepositoryPort repository;

    /**
     * Crea una orden de construcción a partir del record recibido desde el controller.
     *
     * Este método:
     *  • Asegura que las fechas de auditoría siempre estén presentes.
     *  • Asigna el estado inicial IN_PROGRESS.
     *  • Garantiza integridad y consistencia del dominio.
     */
    @Override
    public Mono<ConstructionOrderRecord> create(ConstructionOrderRecord command) {

        LocalDateTime now = LocalDateTime.now();

        ConstructionOrderRecord enriched =
                new ConstructionOrderRecord(
                        null,                          // id → autogenerado
                        command.constructionRequestId(),
                        command.projectId(),
                        command.constructionTypeId(),
                        command.requestedByUserId(),
                        command.latitude(),
                        command.longitude(),
                        command.requestedDate(),
                        command.scheduledStartDate(),
                        command.scheduledEndDate(),
                        OrderStatus.IN_PROGRESS,       // ✔️ Estado inicial correcto
                        now,                           // ✔️ createdAt
                        now,                           // ✔️ updatedAt
                        command.observations(),
                        command.active()
                );

        return repository.save(enriched);
    }
}
