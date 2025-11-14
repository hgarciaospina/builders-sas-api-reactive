
package com.builderssas.api.application.service;
import com.builderssas.api.domain.model.construction.ConstructionType;
import com.builderssas.api.domain.port.out.ConstructionTypeRepository;
import reactor.core.publisher.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor
public class ConstructionTypeService {
  private final ConstructionTypeRepository repo;
  public Flux<ConstructionType> all(){ return repo.findAll(); }
  public Mono<ConstructionType> one(Long id){ return repo.findById(id); }
  public Mono<ConstructionType> save(ConstructionType t){ return repo.save(t); }
  public Mono<Void> delete(Long id){ return repo.deleteById(id); }
}
