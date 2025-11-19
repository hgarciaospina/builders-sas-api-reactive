package com.builderssas.api.infrastructure.web.controller.inventorymovement;

import com.builderssas.api.domain.model.inventory.InventoryMovementRecord;

// Puertos de entrada (casos de uso)
import com.builderssas.api.domain.port.in.inventorymovement.ListInventoryMovementsUseCase;
import com.builderssas.api.domain.port.in.inventorymovement.GetInventoryMovementByIdUseCase;
import com.builderssas.api.domain.port.in.inventorymovement.RegisterInventoryMovementUseCase;
import com.builderssas.api.domain.port.in.inventorymovement.GetInventoryMovementsByMaterialUseCase;
import com.builderssas.api.domain.port.in.inventorymovement.GetInventoryMovementsByOrderUseCase;
import com.builderssas.api.domain.port.in.inventorymovement.ListInventoryMovementsByUserUseCase;

// DTOs y Mapper
import com.builderssas.api.infrastructure.web.dto.inventorymovement.InventoryMovementRequestDto;
import com.builderssas.api.infrastructure.web.dto.inventorymovement.InventoryMovementResponseDto;
import com.builderssas.api.infrastructure.web.mapper.InventoryMovementWebMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador WebFlux encargado de exponer los endpoints
 * del módulo InventoryMovement.
 *
 * Incluye:
 *  ✔ CRUD básico
 *  ✔ Consultas extendidas:
 *      - por material
 *      - por orden
 *      - por usuario
 *
 * No incluye lógica de negocio. Todo se delega a los casos de uso.
 */
@RestController
@RequestMapping("/api/v1/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    // ----------------- Casos de uso CRUD -----------------
    private final ListInventoryMovementsUseCase listUseCase;
    private final GetInventoryMovementByIdUseCase getByIdUseCase;
    private final RegisterInventoryMovementUseCase registerUseCase;

    // ----------------- Casos de uso extendidos -----------------
    private final GetInventoryMovementsByMaterialUseCase getByMaterialUseCase;
    private final GetInventoryMovementsByOrderUseCase getByOrderUseCase;
    private final ListInventoryMovementsByUserUseCase getByUserUseCase;

    private final InventoryMovementWebMapper mapper;

    // ===============================================================
    // CRUD BÁSICO
    // ===============================================================

    /**
     * GET /api/v1/inventory-movements
     * Lista todos los movimientos de inventario.
     */
    @GetMapping
    public Flux<InventoryMovementResponseDto> listAll() {
        return listUseCase.listAll()
                .map(mapper::toResponse);
    }

    /**
     * GET /api/v1/inventory-movements/{id}
     * Obtiene un movimiento por ID.
     */
    @GetMapping("/{id}")
    public Mono<InventoryMovementResponseDto> findById(@PathVariable Long id) {
        return getByIdUseCase.findById(id)
                .map(mapper::toResponse);
    }

    /**
     * POST /api/v1/inventory-movements
     * Registra un movimiento simple.
     */
    @PostMapping
    public Mono<InventoryMovementResponseDto> register(@RequestBody InventoryMovementRequestDto dto) {
        InventoryMovementRecord record = mapper.toDomain(dto);
        return registerUseCase.registerMovement(record)
                .map(mapper::toResponse);
    }

    // ===============================================================
    // CONSULTAS EXTENDIDAS
    // ===============================================================

    /**
     * GET /api/v1/inventory-movements/material/{materialId}
     * Obtiene los movimientos asociados a un material.
     */
    @GetMapping("/material/{materialId}")
    public Flux<InventoryMovementResponseDto> findByMaterial(@PathVariable Long materialId) {
        return getByMaterialUseCase.findByMaterialId(materialId)
                .map(mapper::toResponse);
    }

    /**
     * GET /api/v1/inventory-movements/order/{orderId}
     * Obtiene los movimientos asociados a una orden.
     */
    @GetMapping("/order/{orderId}")
    public Flux<InventoryMovementResponseDto> findByOrder(@PathVariable Long orderId) {
        return getByOrderUseCase.findByOrderId(orderId)
                .map(mapper::toResponse);
    }

    /**
     * GET /api/v1/inventory-movements/user/{userId}
     * Obtiene los movimientos realizados por un usuario específico.
     */
    @GetMapping("/user/{userId}")
    public Flux<InventoryMovementResponseDto> findByUser(@PathVariable Long userId) {
        return getByUserUseCase.findByUserId(userId)
                .map(mapper::toResponse);
    }
}
