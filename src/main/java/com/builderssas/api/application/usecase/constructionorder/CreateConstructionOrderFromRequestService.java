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
 * Caso de uso encargado de generar una nueva orden de construcción
 * a partir de una solicitud existente. Calcula fechas programadas
 * y crea un registro inmutable para persistencia.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionOrderFromRequestService {

    private final ConstructionOrderRepository orderRepository;
    private final ConstructionTypeRepository typeRepository;

    /**
     * Crea una orden de construcción desde una solicitud, calculando:
     * - días estimados según el tipo de construcción,
     * - fecha de inicio encadenada,
     * - fecha final programada.
     *
     * @param req solicitud original
     * @return orden creada lista para persistir
     */
    public Mono<ConstructionOrderRecord> createFromRequest(ConstructionRequestRecord req) {

        // Obtiene los días estimados del tipo de construcción
        Mono<Integer> estimatedDaysMono =
                typeRepository.findById(req.constructionTypeId())
                        .map(ConstructionTypeRecord::estimatedDays);

        // Calcula la fecha programada de inicio, encadenando con la orden previa o la solicitud
        Mono<LocalDate> scheduledStartMono =
                orderRepository.findLastByProjectId(req.projectId())
                        .map(lastOrder -> lastOrder.scheduledEndDate().plusDays(1))
                        .defaultIfEmpty(req.requestDate().plusDays(1));

        return estimatedDaysMono
                .zipWith(scheduledStartMono)
                .map(tuple -> {

                    Integer estimatedDays = tuple.getT1();
                    LocalDate startDate = tuple.getT2();
                    LocalDate endDate = startDate.plusDays(estimatedDays).plusDays(1);

                    return new ConstructionOrderRecord(
                            null,                         // id generado al guardar
                            req.id(),                     // requestId
                            req.projectId(),              // projectId
                            req.constructionTypeId(),     // constructionTypeId
                            req.requestedByUserId(),        // createdByUserId
                            req.latitude(),               // latitude
                            req.longitude(),              // longitude
                            req.requestDate(),            // requestedDate
                            startDate,                    // scheduledStartDate
                            endDate,                      // scheduledEndDate
                            OrderStatus.PENDING,          // orderStatus
                            LocalDateTime.now(),          // createdAt
                            null,                         // updatedAt (aún no existe)
                            req.observations(),           // observations
                            true                          // active
                    );
                })
                .flatMap(orderRepository::save);
    }
}
