package com.builderssas.api.infrastructure.web.controller.constructiontype;

import com.builderssas.api.domain.port.in.constructiontype.*;
import com.builderssas.api.infrastructure.web.dto.constructiontype.*;
import com.builderssas.api.infrastructure.web.mapper.ConstructionTypeWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/construction-types")
@RequiredArgsConstructor
public class ConstructionTypeController {

    private final CreateConstructionTypeUseCase createUseCase;
    private final GetConstructionTypeUseCase getUseCase;
    private final ListConstructionTypesUseCase listUseCase;
    private final ListActiveConstructionTypesUseCase listActiveUseCase;
    private final UpdateConstructionTypeUseCase updateUseCase;
    private final DeleteConstructionTypeUseCase deleteUseCase;

    /**
     * Crea un nuevo tipo de construcción.
     */
    @PostMapping
    public Mono<ConstructionTypeResponseDto> create(
            @Valid @RequestBody ConstructionTypeCreateDto dto
    ) {
        return Mono.just(dto)
                .map(ConstructionTypeWebMapper::toRecord)
                .flatMap(createUseCase::create)
                .map(ConstructionTypeWebMapper::toResponse);
    }

    /**
     * Consulta un tipo de construcción por ID.
     */
    @GetMapping("/{id}")
    public Mono<ConstructionTypeResponseDto> getById(@PathVariable Long id) {
        return getUseCase.getById(id)
                .map(ConstructionTypeWebMapper::toResponse);
    }

    /**
     * Lista solo los tipos activos.
     */
    @GetMapping
    public Flux<ConstructionTypeResponseDto> listActive() {
        return listActiveUseCase.listActive()
                .map(ConstructionTypeWebMapper::toResponse);
    }

    /**
     * Lista todos los tipos de construcción.
     */
    @GetMapping("/all")
    public Flux<ConstructionTypeResponseDto> listAll() {
        return listUseCase.listAll()
                .map(ConstructionTypeWebMapper::toResponse);
    }

    /**
     * Actualiza un tipo de construcción.
     */
    @PutMapping("/{id}")
    public Mono<ConstructionTypeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ConstructionTypeUpdateDto dto
    ) {
        return Mono.just(dto)
                .map(d -> ConstructionTypeWebMapper.toRecord(id, d))
                .flatMap(r -> updateUseCase.update(id, r))
                .map(ConstructionTypeWebMapper::toResponse);
    }

    /**
     * Borrado lógico.
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
