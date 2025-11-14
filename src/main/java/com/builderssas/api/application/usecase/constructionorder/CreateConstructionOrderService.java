package com.builderssas.api.application.usecase.constructionorder;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import com.builderssas.api.domain.port.in.constructionorder.CreateConstructionOrderUseCase;
import com.builderssas.api.domain.port.out.ConstructionOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateConstructionOrderService implements CreateConstructionOrderUseCase {

    private final ConstructionOrderRepository repository;

    @Override
    public Mono<ConstructionOrder> create(ConstructionOrder command) {
        return repository.save(command);
    }
}
