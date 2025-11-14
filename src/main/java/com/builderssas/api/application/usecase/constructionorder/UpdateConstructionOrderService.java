package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import com.builderssas.api.domain.port.in.constructionorder.UpdateConstructionOrderUseCase;
import com.builderssas.api.domain.port.out.ConstructionOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateConstructionOrderService implements UpdateConstructionOrderUseCase {

    private final ConstructionOrderRepository repository;

    @Override
    public Mono<ConstructionOrder> update(Long id, ConstructionOrder command) {
        ConstructionOrder updated = new ConstructionOrder(
            id,
            requestId, status, dayCount, active
        );
        return repository.save(updated);
    }
}
