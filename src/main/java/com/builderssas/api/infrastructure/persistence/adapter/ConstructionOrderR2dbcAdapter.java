package com.builderssas.api.infrastructure.persistence.adapter;

import com.builderssas.api.domain.model.constructionorder.ConstructionOrderRecord;
import com.builderssas.api.domain.model.enums.OrderStatus;
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

    // ------------------------
    //     ENTITY → DOMAIN
    // ------------------------
    private ConstructionOrderRecord toDomain(ConstructionOrderEntity e) {
        if (e == null) return null;

        return new ConstructionOrderRecord(
                e.getId(),
                e.getRequestId(),
                e.getProjectId(),
                e.getConstructionTypeId(),
                e.getCreatedByUserId(),
                e.getLatitude(),
                e.getLongitude(),
                e.getEstimatedDays(),
                e.getStartDate(),
                e.getEndDate(),
                e.getStatus() != null ? OrderStatus.valueOf(e.getStatus()) : null,
                e.getActive()
        );
    }

    // ------------------------
    //     DOMAIN → ENTITY
    // ------------------------
    private ConstructionOrderEntity toEntity(ConstructionOrderRecord d) {
        if (d == null) return null;

        ConstructionOrderEntity e = new ConstructionOrderEntity();

        e.setId(d.id());
        e.setRequestId(d.requestId());
        e.setProjectId(d.projectId());
        e.setConstructionTypeId(d.constructionTypeId());
        e.setCreatedByUserId(d.createdByUserId());
        e.setLatitude(d.latitude());
        e.setLongitude(d.longitude());
        e.setEstimatedDays(d.estimatedDays());
        e.setStartDate(d.startDate());
        e.setEndDate(d.endDate());
        e.setStatus(d.status() != null ? d.status().name() : null);
        e.setActive(d.active());

        return e;
    }

    // ------------------------
    //       OPERACIONES
    // ------------------------
    @Override
    public Mono<ConstructionOrderRecord> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<ConstructionOrderRecord> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<ConstructionOrderRecord> save(ConstructionOrderRecord aggregate) {
        return repository.save(toEntity(aggregate)).map(this::toDomain);
    }
}
