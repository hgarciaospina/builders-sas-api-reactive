package com.builderssas.api.infrastructure.web.controller.constructionrequest;

import com.builderssas.api.domain.port.in.constructionrequest.CreateConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.GetConstructionRequestUseCase;
import com.builderssas.api.domain.port.in.constructionrequest.ListConstructionRequestsUseCase;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;
import com.builderssas.api.infrastructure.web.mapper.ConstructionRequestWebMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador encargado de exponer los endpoints relacionados con solicitudes
 * de construcción. Su responsabilidad se limita a:
 *
 *   • Recibir y validar solicitudes HTTP.
 *   • Delegar al caso de uso correspondiente.
 *   • Mapear DTOs ↔ Records del dominio.
 *
 * No contiene lógica de negocio ni mutaciones, siguiendo la Arquitectura
 * Hexagonal estricta y el modelo reactivo no bloqueante.
 */
@RestController
@RequestMapping("/api/construction-requests")
public class ConstructionRequestController {

    private final CreateConstructionRequestUseCase createUseCase;
    private final GetConstructionRequestUseCase getUseCase;
    private final ListConstructionRequestsUseCase listUseCase;
    private final ConstructionRequestWebMapper webMapper;

    public ConstructionRequestController(
            CreateConstructionRequestUseCase createUseCase,
            GetConstructionRequestUseCase getUseCase,
            ListConstructionRequestsUseCase listUseCase,
            ConstructionRequestWebMapper webMapper
    ) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
        this.webMapper = webMapper;
    }

    /**
     * Crea una nueva solicitud de construcción.
     *
     * @param dto datos de entrada enviados por el cliente
     * @return DTO de respuesta representando la solicitud creada
     */
    @PostMapping
    public Mono<ConstructionRequestResponseDto> create(
            @Valid @RequestBody ConstructionRequestCreateDto dto
    ) {
        return Mono.just(dto)
                .map(webMapper::toRecord)
                .flatMap(createUseCase::create)
                .map(webMapper::toResponse);
    }

    /**
     * Recupera una solicitud específica según su ID.
     *
     * @param id identificador único de la solicitud
     * @return DTO de respuesta correspondiente
     */
    @GetMapping("/{id}")
    public Mono<ConstructionRequestResponseDto> findById(@PathVariable Long id) {
        return getUseCase.findById(id)
                .map(webMapper::toResponse);
    }

    /**
     * Lista todas las solicitudes almacenadas.
     *
     * @return flujo reactivo de solicitudes convertidas a DTO
     */
    @GetMapping
    public Flux<ConstructionRequestResponseDto> listAll() {
        return listUseCase.listAll()
                .map(webMapper::toResponse);
    }
}
