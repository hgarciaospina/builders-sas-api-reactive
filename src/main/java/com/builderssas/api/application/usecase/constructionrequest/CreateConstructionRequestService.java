package com.builderssas.api.application.usecase.constructionrequest;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderFactory;
import com.builderssas.api.domain.model.constructionrequest.ConstructionRequestRecord;
import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import com.builderssas.api.domain.port.out.constructionrequest.ConstructionRequestRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para crear solicitudes de construcción.
 *
 *  • Persiste la solicitud.
 *  • Genera la orden automáticamente usando la Factory del dominio.
 *  • No contiene reglas de negocio ni lógica calculada.
 *  • 100% funcional, inmutable y reactivo.
 */
@Service
@RequiredArgsConstructor
public class CreateConstructionRequestService implements CreateConstructionRequestUseCase {

    private final ConstructionRequestRepositoryPort requestRepository;
    private final ConstructionOrderRepositoryPort orderRepository;

    @Override
    public Mono<ConstructionRequestRecord> create(ConstructionRequestRecord command) {

        return Mono.just(command)
                .flatMap(requestRepository::save)
                .flatMap(savedRequest ->
                        Mono.just(savedRequest)
                                .map(ConstructionOrderFactory::fromRequest)
                                .flatMap(orderRepository::save)
                                .thenReturn(savedRequest)
                );
    }
}