package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Caso de uso encargado de **crear órdenes de construcción a partir
 * de una solicitud aprobada (ConstructionRequestRecord)**.
 *
 * Características arquitectónicas:
 *  • Pertenece a la capa de aplicación (Application Layer).
 *  • No contiene lógica de infraestructura.
 *  • NO muta objetos — genera records totalmente inmutables.
 *  • Calcula:
 *      - Fecha de inicio programada.
 *      - Fecha de finalización estimada.
 *      - Estado inicial de la orden.
 *      - Fechas de auditoría (createdAt / updatedAt).
 *
 * Flujo:
 *  1. Consulta el tipo de construcción para obtener los días estimados.
 *  2. Obtiene la última orden del proyecto para encadenar fechas.
 *  3. Calcula startDate y endDate.
 *  4. Construye un ConstructionOrderRecord inmutable.
 *  5. Persiste la orden mediante el puerto de repositorio.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionOrderFromRequestService {

    private final ConstructionOrderRepositoryPort orderRepository;
    private final ConstructionTypeRepositoryPort typeRepository;

    /**
     * Crea una Orden de Construcción a partir de una solicitud validada.
     *
     * @param request ConstructionRequestRecord aprobado
     * @return Mono<ConstructionOrderRecord> orden persistida
     */
    public Mono<ConstructionOrderRecord> createFromRequest(ConstructionRequestRecord request) {

        // 1️⃣ Obtener días estimados desde el tipo de construcción
        Mono<Integer> estimatedDaysMono =
                typeRepository.findById(request.constructionTypeId())
                        .map(ConstructionTypeRecord::estimatedDays);

        // 2️⃣ Calcular fecha de inicio:
        //    • Si existe una orden previa → el día siguiente a su fin
        //    • Si no existe → requestDate + 1 día
        Mono<LocalDate> startDateMono =
                orderRepository.findLastByProjectId(request.projectId())
                        .map(last -> last.scheduledEndDate().plusDays(1))
                        .defaultIfEmpty(request.requestDate().plusDays(1));

        /**
         * Record interno para evitar getT1() / getT2()
         * y mantener el código 100% legible.
         */
        record OrderCreationData(Integer estimatedDays, LocalDate startDate) {}

        // 3️⃣ Unir cálculos funcionalmente de forma legible
        return estimatedDaysMono
                .zipWith(startDateMono, OrderCreationData::new)
                .map(data -> {

                    Integer estimated = data.estimatedDays();
                    LocalDate start = data.startDate();
                    LocalDate end = start.plusDays(estimated).plusDays(1);

                    LocalDateTime now = LocalDateTime.now();

                    // 4️⃣ Record inmutable creado correctamente
                    return new ConstructionOrderRecord(
                            null,                         // id → autogenerado
                            request.id(),                 // FK solicitud
                            request.projectId(),
                            request.constructionTypeId(),
                            request.requestedByUserId(),
                            request.latitude(),
                            request.longitude(),
                            request.requestDate(),        // requestedDate
                            start,                        // scheduledStartDate
                            end,                          // scheduledEndDate
                            OrderStatus.IN_PROGRESS,      // ✔️ Estado inicial correcto
                            now,                          // createdAt
                            now,                          // updatedAt
                            request.observations(),
                            true                          // active
                    );
                })
                .flatMap(orderRepository::save);
    }
}
