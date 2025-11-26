package com.builderssas.api.infrastructure.web.controller.inventorymovement;

import com.builderssas.api.domain.model.inventorymovement.InventoryMovementRecord;

// Casos de uso
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

@RestController
@RequestMapping("/api/v1/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final ListInventoryMovementsUseCase listUseCase;
    private final GetInventoryMovementByIdUseCase getByIdUseCase;
    private final RegisterInventoryMovementUseCase registerUseCase;

    private final GetInventoryMovementsByMaterialUseCase getByMaterialUseCase;
    private final GetInventoryMovementsByOrderUseCase getByOrderUseCase;
    private final ListInventoryMovementsByUserUseCase getByUserUseCase;

    @GetMapping
    public Flux<InventoryMovementResponseDto> listAll() {
        return listUseCase.listAll()
                .map(InventoryMovementWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<InventoryMovementResponseDto> findById(@PathVariable Long id) {
        return getByIdUseCase.findById(id)
                .map(InventoryMovementWebMapper::toResponse);
    }

    @PostMapping
    public Mono<InventoryMovementResponseDto> register(@RequestBody InventoryMovementRequestDto dto) {
        InventoryMovementRecord record = InventoryMovementWebMapper.toDomain(dto);
        return registerUseCase.registerMovement(record)
                .map(InventoryMovementWebMapper::toResponse);
    }

    @GetMapping("/material/{materialId}")
    public Flux<InventoryMovementResponseDto> findByMaterial(@PathVariable Long materialId) {
        return getByMaterialUseCase.findByMaterialId(materialId)
                .map(InventoryMovementWebMapper::toResponse);
    }

    @GetMapping("/order/{orderId}")
    public Flux<InventoryMovementResponseDto> findByOrder(@PathVariable Long orderId) {
        return getByOrderUseCase.findByOrderId(orderId)
                .map(InventoryMovementWebMapper::toResponse);
    }

    @GetMapping("/user/{userId}")
    public Flux<InventoryMovementResponseDto> findByUser(@PathVariable Long userId) {
        return getByUserUseCase.findByUserId(userId)
                .map(InventoryMovementWebMapper::toResponse);
    }
}
