package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.application.usecase.constructionrequest.CreateConstructionRequestService;
import com.builderssas.api.application.usecase.constructionrequest.GetConstructionRequestService;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestCreateDto;
import com.builderssas.api.infrastructure.web.dto.constructionrequest.ConstructionRequestResponseDto;
import com.builderssas.api.infrastructure.web.mapper.ConstructionRequestWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;

/**
 * Controlador Web para gestionar solicitudes de construcción.
 *
 * Este controlador:
 * - No contiene lógica de negocio.
 * - No realiza validaciones manuales.
 * - Delegación completa a casos de uso del dominio.
 * - Utiliza DTOs para entrada y salida.
 * - Mantiene alineación 100% con arquitectura hexagonal.
 */
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ConstructionRequestController {

    private final CreateConstructionRequestService createService;
    private final GetConstructionRequestService getService;
    private final ConstructionRequestWebMapper webMapper;

    /**
     * Crea una nueva solicitud de construcción.
     *
     * @param dto datos de entrada validados
     * @return solicitud creada en formato DTO de respuesta
     */
    @PostMapping
    public Mono<ConstructionRequestResponseDto> create(
            @Valid @RequestBody ConstructionRequestCreateDto dto
    ) {
        return createService.create(
                        webMapper.toRecord(dto)  // requestedByUserId queda pendiente hasta activar JWT
                )
                .map(webMapper::toResponse);
    }

    /**
     * Obtiene una solicitud según su ID.
     *
     * @param id identificador de la solicitud
     * @return solicitud en formato DTO
     */
    @GetMapping("/{id}")
    public Mono<ConstructionRequestResponseDto> findById(@PathVariable Long id) {
        return getService.findById(id)
                .map(webMapper::toResponse);
    }

    /**
     * Obtiene todas las solicitudes de construcción almacenadas.
     *
     * @return flujo de solicitudes en formato DTO
     */
    @GetMapping
    public Flux<ConstructionRequestResponseDto> findAll() {
        return getService.findAll()
                .map(webMapper::toResponse);
    }
}
