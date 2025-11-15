package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
import com.builderssas.api.domain.port.out.ConstructionOrderRepository;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Caso de uso encargado de crear una orden de construcción desde una solicitud.
 *
 * Reglas exactas del negocio:
 *
 * 1. Si el proyecto NO tiene órdenes previas:
 *      scheduledStartDate = requestDate + 1 día
 *      scheduledEndDate   = scheduledStartDate + estimatedDays + 1 día de entrega
 *
 * 2. Si el proyecto SÍ tiene órdenes previas:
 *      scheduledStartDate = scheduledEndDate última orden + 1 día
 *      scheduledEndDate   = scheduledStartDate + estimatedDays + 1 día de entrega
 *
 * El día adicional al final SIEMPRE corresponde al día de entrega.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionOrderFromRequestService {

    private final ConstructionOrderRepository orderRepository;
    private final ConstructionTypeRepository typeRepository;

    /**
     * Genera la orden aplicando las reglas de encadenamiento descritas arriba.
     */
    public Mono<ConstructionOrderRecord> createFromRequest(ConstructionRequestRecord req) {

        // Obtiene estimatedDays desde el tipo de construcción
        Mono<Integer> estimatedDaysMono =
                typeRepository.findById(req.constructionTypeId())
                        .map(ConstructionTypeRecord::estimatedDays);

        // Determina scheduledStartDate según haya o no órdenes previas
        Mono<LocalDate> scheduledStartMono =
                orderRepository.findLastByProjectId(req.projectId())
                        .map(lastOrder -> lastOrder.scheduledEndDate().plusDays(1))  // Caso 2
                        .defaultIfEmpty(req.requestDate().plusDays(1));               // Caso 1

        return estimatedDaysMono
                .zipWith(scheduledStartMono)
                .map(tuple -> {

                    Integer estimatedDays = tuple.getT1();
                    LocalDate scheduledStartDate = tuple.getT2();

                    // scheduledEndDate = scheduledStartDate + estimatedDays + 1 día de entrega
                    LocalDate scheduledEndDate = scheduledStartDate
                            .plusDays(estimatedDays)
                            .plusDays(1);

                    return new ConstructionOrderRecord(
                            null,
                            req.id(),
                            req.projectId(),
                            req.constructionTypeId(),
                            req.requestedByUserId(),
                            req.latitude(),
                            req.longitude(),
                            req.requestDate(),
                            scheduledStartDate,
                            scheduledEndDate,
                            OrderStatus.PENDING,
                            LocalDateTime.now(),
                            null,
                            req.observations(),
                            true
                    );
                })
                .flatMap(orderRepository::save);
    }
}
