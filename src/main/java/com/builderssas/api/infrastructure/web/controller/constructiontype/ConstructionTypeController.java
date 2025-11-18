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
    private final ConstructionTypeWebMapper webMapper;

    @PostMapping
    public Mono<ConstructionTypeResponseDto> create(
            @Valid @RequestBody ConstructionTypeCreateDto dto
    ) {
        return Mono.just(dto)
                .map(webMapper::toRecord)
                .flatMap(createUseCase::create)
                .map(webMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ConstructionTypeResponseDto> getById(@PathVariable Long id) {
        return getUseCase.getById(id)
                .map(webMapper::toResponse);
    }

    @GetMapping
    public Flux<ConstructionTypeResponseDto> listActive() {
        return listActiveUseCase.listActive()
                .map(webMapper::toResponse);
    }

    @GetMapping("/all")
    public Flux<ConstructionTypeResponseDto> listAll() {
        return listUseCase.listAll()
                .map(webMapper::toResponse);
    }

    @PutMapping("/{id}")
    public Mono<ConstructionTypeResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ConstructionTypeUpdateDto dto
    ) {
        return Mono.just(dto)
                .map(d -> webMapper.toRecord(id, d))
                .flatMap(r -> updateUseCase.update(id, r))
                .map(webMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
