package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.role.Role;
import com.builderssas.api.domain.port.in.role.CreateRoleUseCase;
import com.builderssas.api.domain.port.in.role.UpdateRoleUseCase;
import com.builderssas.api.domain.port.in.role.GetRoleUseCase;
import com.builderssas.api.domain.port.in.role.ListRolesUseCase;
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
    private final GetRoleUseCase getUseCase;
    private final ListRolesUseCase listUseCase;

    @GetMapping
    public Flux<Role> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<Role> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Role> create(@RequestBody Role body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<Role> update(@PathVariable Long id, @RequestBody Role body) {
        return updateUseCase.update(id, body);
    }
}
