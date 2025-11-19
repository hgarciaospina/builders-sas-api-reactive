package com.builderssas.api.infrastructure.web.controller.constructiontypematerial;

import com.builderssas.api.domain.model.constructiontypematerial.ConstructionTypeMaterialRecord;
import com.builderssas.api.domain.port.in.constructiontypematerial.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador WebFlux para ConstructionTypeMaterial.
 *
 * Expone todas las operaciones CRUD reactivas:
 *  • Crear
 *  • Actualizar
 *  • Obtener por ID
 *  • Listar todos
 *  • Listar activos
 *  • Soft delete
 *
 * 100% reactivo y sin lógica de negocio.
 */
@RestController
@RequestMapping("/api/v1/construction-type-materials")
@RequiredArgsConstructor
public final class ConstructionTypeMaterialController {

    private final CreateConstructionTypeMaterialUseCase createUseCase;
    private final UpdateConstructionTypeMaterialUseCase updateUseCase;
    private final GetConstructionTypeMaterialUseCase getUseCase;
    private final ListConstructionTypeMaterialUseCase listUseCase;
    private final ListActiveConstructionTypeMaterialUseCase listActiveUseCase;
    private final DeleteConstructionTypeMaterialUseCase deleteUseCase;

    /**
     * Crear un nuevo registro de ConstructionTypeMaterial.
     */
    @PostMapping
    public Mono<ConstructionTypeMaterialRecord> create(
            @RequestBody ConstructionTypeMaterialRecord record
    ) {
        return createUseCase.create(record);
    }

    /**
     * Actualizar un registro existente.
     */
    @PutMapping("/{id}")
    public Mono<ConstructionTypeMaterialRecord> update(
            @PathVariable Long id,
            @RequestBody ConstructionTypeMaterialRecord record
    ) {
        return updateUseCase.update(id, record);
    }

    /**
     * Obtener un registro por ID.
     */
    @GetMapping("/{id}")
    public Mono<ConstructionTypeMaterialRecord> findById(@PathVariable Long id) {
        return getUseCase.findById(id);
    }

    /**
     * Listar todos los registros.
     */
    @GetMapping("/all")
    public Flux<ConstructionTypeMaterialRecord> findAll() {
        return listUseCase.findAll();
    }

    /**
     * Listar únicamente los registros activos.
     */
    @GetMapping("/active")
    public Flux<ConstructionTypeMaterialRecord> findAllActive() {
        return listActiveUseCase.listActive();
    }

    /**
     * Soft delete (marcar como inactivo).
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteUseCase.delete(id);
    }
}
