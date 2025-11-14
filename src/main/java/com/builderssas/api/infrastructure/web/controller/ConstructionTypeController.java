
package com.builderssas.api.infrastructure.web.controller;
import com.builderssas.api.application.service.ConstructionTypeService;
import com.builderssas.api.domain.model.construction.ConstructionType;
import reactor.core.publisher.*;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
@RestController @RequestMapping("/api/v1/construction-types") @RequiredArgsConstructor
public class ConstructionTypeController {
  private final ConstructionTypeService service;
  @GetMapping public Flux<ConstructionType> all(){ return service.all();}
  @GetMapping("/{id}") public Mono<ConstructionType> one(@PathVariable Long id){ return service.one(id);}
  @PostMapping public Mono<ConstructionType> create(@RequestBody ConstructionType t){ return service.save(t);}
}
