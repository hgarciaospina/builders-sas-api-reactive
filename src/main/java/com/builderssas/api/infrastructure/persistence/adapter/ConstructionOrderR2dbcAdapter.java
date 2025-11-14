package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrder;
import com.builderssas.api.domain.port.out.ConstructionOrderRepository;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionOrderEntity;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionOrderR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ConstructionOrderR2dbcAdapter implements ConstructionOrderRepository {

    private final ConstructionOrderR2dbcRepository repository;


private ConstructionOrder toDomain(ConstructionOrderEntity e) {
    if (e == null) return null;
    return new ConstructionOrder(
        e.getId(),
            e.getRequestId(),
        e.getStatus(),
        e.getDayCount(),
        e.getActive()
    );
}

private ConstructionOrderEntity toEntity(ConstructionOrder d) {
    if (d == null) return null;
    ConstructionOrderEntity e = new ConstructionOrderEntity();
    e.setId(d.id());
    e.setRequestId(d.requestId());
    e.setStatus(d.status());
    e.setDayCount(d.dayCount());
    e.setActive(d.active());
    return e;
}


    @Override
    public Mono<ConstructionOrder> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<ConstructionOrder> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<ConstructionOrder> save(ConstructionOrder aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
