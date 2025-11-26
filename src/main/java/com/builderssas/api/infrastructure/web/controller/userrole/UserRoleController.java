package com.builderssas.api.infrastructure.web.controller.userrole;

import com.builderssas.api.domain.model.userrole.UserRoleRecord;
import com.builderssas.api.domain.port.in.userrole.*;
import com.builderssas.api.infrastructure.web.dto.userrole.CreateUserRoleDto;
import com.builderssas.api.infrastructure.web.dto.userrole.DeleteUserRoleDto;
import com.builderssas.api.infrastructure.web.dto.userrole.UpdateUserRoleDto;
import com.builderssas.api.infrastructure.web.dto.userrole.UserRoleDto;

import com.builderssas.api.infrastructure.web.mapper.UserRoleWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador WebFlux para la gestión de relaciones usuario-rol (UserRole).
 *
 * Este controlador sigue estrictamente la arquitectura hexagonal:
 * - Recibe DTOs desde HTTP.
 * - Usa UserRoleWebMapper para convertir DTO ↔ Record.
 * - Delegación total a casos de uso.
 * - Sin lógica de negocio.
 * - Sin exponer el modelo de dominio al cliente.
 */
@RestController
@RequestMapping("/api/v1/user-roles")
@RequiredArgsConstructor
public class UserRoleController {

    private final CreateUserRoleUseCase createUseCase;
    private final GetUserRoleByIdUseCase getByIdUseCase;
    private final GetAllUserRolesUseCase getAllUseCase;
    private final FindUserRolesByUserIdUseCase findByUserIdUseCase;
    private final UpdateUserRoleUseCase updateUseCase;
    private final DeleteUserRoleUseCase deleteUseCase;

    // ===================================================
    // CREATE
    // ===================================================
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserRoleDto> create(@Valid @RequestBody CreateUserRoleDto dto) {
        UserRoleRecord record = UserRoleWebMapper.toDomain(dto);
        return createUseCase.create(record)
                .map(UserRoleWebMapper::toDto);
    }

    // ===================================================
    // GET BY ID
    // ===================================================
    @GetMapping("/{id}")
    public Mono<UserRoleDto> getById(@PathVariable Long id) {
        return getByIdUseCase.getById(id)
                .map(UserRoleWebMapper::toDto);
    }


    // ===================================================
    // LIST ALL
    // ===================================================
    @GetMapping
    public Flux<UserRoleDto> getAll() {
        return getAllUseCase.getAll()
                .map(UserRoleWebMapper::toDto);
    }

    // ===================================================
    // GET ROLES BY USER
    // ===================================================
    @GetMapping("/user/{userId}")
    public Flux<UserRoleDto> getByUserId(@PathVariable Long userId) {
        return findByUserIdUseCase.findByUserId(userId)
                .map(UserRoleWebMapper::toDto);
    }


    // ===================================================
    // UPDATE (solo active)
    // ===================================================
    @PutMapping
    public Mono<UserRoleDto> update(@Valid @RequestBody UpdateUserRoleDto dto) {

        return getByIdUseCase.getById(dto.id())                             // 1. leer actual
                .map(existing -> UserRoleWebMapper.toDomain(dto, existing))  // 2. reconstruir record inmutable
                .flatMap(updateUseCase::update)                              // 3. delegar al caso de uso (UserRoleRecord)
                .map(UserRoleWebMapper::toDto);                              // 4. convertir record actualizado → DTO de salida
    }

    // ===================================================
    // DELETE (soft delete)
    // ===================================================
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@Valid @RequestBody DeleteUserRoleDto dto) {
        return deleteUseCase.delete(dto.id());
    }
}
