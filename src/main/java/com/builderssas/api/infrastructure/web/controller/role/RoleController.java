package com.builderssas.api.infrastructure.web.controller.role;

import com.builderssas.api.domain.port.in.role.CreateRoleUseCase;
import com.builderssas.api.domain.port.in.role.UpdateRoleUseCase;
import com.builderssas.api.domain.port.in.role.GetRoleByIdUseCase;
import com.builderssas.api.domain.port.in.role.ListRolesUseCase;
import com.builderssas.api.domain.port.in.role.GetRoleByNameUseCase;
import com.builderssas.api.domain.port.in.role.GetRoleByDescriptionUseCase;
import com.builderssas.api.domain.port.in.role.SoftDeleteRoleUseCase;
import com.builderssas.api.domain.port.in.role.ToggleRoleStatusUseCase;
import com.builderssas.api.infrastructure.web.dto.role.CreateRoleDto;
import com.builderssas.api.infrastructure.web.dto.role.UpdateRoleDto;
import com.builderssas.api.infrastructure.web.dto.role.RoleResponseDto;
import com.builderssas.api.infrastructure.web.mapper.RoleWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final CreateRoleUseCase createUseCase;
    private final UpdateRoleUseCase updateUseCase;
    private final GetRoleByIdUseCase getByIdUseCase;
    private final GetRoleByNameUseCase getByNameUseCase;
    private final GetRoleByDescriptionUseCase getByDescriptionUseCase;
    private final ListRolesUseCase listUseCase;
    private final SoftDeleteRoleUseCase deleteUseCase;
    private final ToggleRoleStatusUseCase toggleUseCase;

    @GetMapping
    public Flux<RoleResponseDto> list() {
        return listUseCase.listAll()
                .map(RoleWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<RoleResponseDto> getById(@PathVariable Long id) {
        return getByIdUseCase.findById(id)
                .map(RoleWebMapper::toResponse);
    }

    @GetMapping("/name/{name}")
    public Mono<RoleResponseDto> getByName(@PathVariable String name) {
        return getByNameUseCase.findByName(name)
                .map(RoleWebMapper::toResponse);
    }

    @GetMapping("/description/{description}")
    public Mono<RoleResponseDto> getByDescription(@PathVariable String description) {
        return getByDescriptionUseCase.findByDescription(description)
                .map(RoleWebMapper::toResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RoleResponseDto> create(@Valid @RequestBody CreateRoleDto dto) {
        return createUseCase.create(RoleWebMapper.toDomain(dto))
                .map(RoleWebMapper::toResponse);
    }

    @PutMapping
    public Mono<RoleResponseDto> update(@Valid @RequestBody UpdateRoleDto dto) {
        return updateUseCase.update(
                        dto.id(),
                        RoleWebMapper.toDomain(dto)
                )
                .map(RoleWebMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    public Mono<RoleResponseDto> delete(@PathVariable Long id) {
        return deleteUseCase.softDelete(id)
                .map(RoleWebMapper::toResponse);
    }

    @PutMapping("/toggle/{id}")
    public Mono<RoleResponseDto> toggle(@PathVariable Long id) {
        return toggleUseCase.toggleStatus(id)
                .map(RoleWebMapper::toResponse);
    }
}
