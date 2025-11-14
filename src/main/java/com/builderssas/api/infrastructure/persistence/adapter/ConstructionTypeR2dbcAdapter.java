
package com.builderssas.api.infrastructure.persistence.adapter;
import com.builderssas.api.domain.model.construction.ConstructionType;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import com.builderssas.api.infrastructure.persistence.repository.ConstructionTypeR2dbcRepository;
import com.builderssas.api.infrastructure.persistence.entity.ConstructionTypeEntity;
import reactor.core.publisher.*;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
@Component @RequiredArgsConstructor
public class ConstructionTypeR2dbcAdapter implements ConstructionTypeRepository {
  private final ConstructionTypeR2dbcRepository repo;
  private ConstructionType toDomain(ConstructionTypeEntity e){ return new ConstructionType(e.getId(),e.getName(),e.getEstimatedDays());}
  private ConstructionTypeEntity toEntity(ConstructionType d){ var e=new ConstructionTypeEntity(); e.setId(d.id());e.setName(d.name());e.setEstimatedDays(d.estimatedDays());return e;}
  public Mono<ConstructionType> findById(Long id){ return repo.findById(id).map(this::toDomain);}
  public Flux<ConstructionType> findAll(){ return repo.findAll().map(this::toDomain);}
  public Mono<ConstructionType> save(ConstructionType t){ return repo.save(toEntity(t)).map(this::toDomain);}
  public Mono<Void> deleteById(Long id){ return repo.deleteById(id);}
}
