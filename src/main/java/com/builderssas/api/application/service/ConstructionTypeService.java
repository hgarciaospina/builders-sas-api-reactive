package com.builderssas.api.application.service;

import com.builderssas.api.domain.model.construction.ConstructionTypeRecord;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConstructionTypeService {

  private final ConstructionTypeRepositoryPort repo;

  public Flux<ConstructionTypeRecord> all() {
    return repo.findAll();
  }

  public Mono<ConstructionTypeRecord> one(Long id) {
    return repo.findById(id);
  }

  public Mono<ConstructionTypeRecord> save(ConstructionTypeRecord type) {
    return repo.save(type);
  }

  /**
   * NO usamos delete físico.
   * Si en algún futuro quieres soporte para "eliminación lógica",
   * aquí solo actualizaríamos el campo active = false.
   */
  public Mono<Void> delete(Long id) {
    return Mono.empty(); // o repo.softDelete(id) si se implementa
  }
}
