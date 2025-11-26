package com.builderssas.api.infrastructure.web.controller.constructionrequest;

import com.builderssas.api.application.usecase.constructionrequest.ConstructionRequestOrchestratorService;
import com.builderssas.api.application.usecase.constructionrequest.query.ConstructionRequestQueryService;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;
import com.builderssas.api.infrastructure.web.mapper.ConstructionRequestWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/construction-requests")
public class ConstructionRequestController {

    private final ConstructionRequestOrchestratorService orchestrator;
    private final ConstructionRequestQueryService queryService;

    // ============================================================
    //                      CREATE REQUEST
    // ============================================================
    @PostMapping
    public Mono<ConstructionRequestResponseDto> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ConstructionRequestCreateDto dto
    ) {
        return Mono.just(dto)
                .map(d -> ConstructionRequestWebMapper.toRecord(d, userId))
                .flatMap(orchestrator::create)
                .map(ConstructionRequestWebMapper::toResponse);
    }

    // ============================================================
    //                      GET BY ID
    // ============================================================
    @GetMapping("/{id}")
    public Mono<ConstructionRequestResponseDto> findById(@PathVariable Long id) {
        return queryService.findById(id)
                .map(ConstructionRequestWebMapper::toResponse);
    }

    // ============================================================
    //                      LIST ALL
    // ============================================================
    @GetMapping
    public Flux<ConstructionRequestResponseDto> listAll() {
        return queryService.listAll()
                .map(ConstructionRequestWebMapper::toResponse);
    }
}
