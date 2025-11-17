package com.builderssas.api.infrastructure.web.controller.role;

import com.builderssas.api.domain.model.role.RoleRecord;
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
    public Flux<RoleRecord> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<RoleRecord> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RoleRecord> create(@RequestBody RoleRecord body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<RoleRecord> update(@PathVariable Long id, @RequestBody RoleRecord body) {
        return updateUseCase.update(id, body);
    }
}
