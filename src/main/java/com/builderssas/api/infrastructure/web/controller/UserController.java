package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.CreateUserUseCase;
import com.builderssas.api.domain.port.in.user.UpdateUserUseCase;
import com.builderssas.api.domain.port.in.user.GetUserUseCase;
import com.builderssas.api.domain.port.in.user.ListUsersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUseCase;
    private final UpdateUserUseCase updateUseCase;
    private final GetUserUseCase getUseCase;
    private final ListUsersUseCase listUseCase;

    @GetMapping
    public Flux<UserRecord> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<UserRecord> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserRecord> create(@RequestBody UserRecord body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<UserRecord> update(@PathVariable Long id, @RequestBody UserRecord body) {
        return updateUseCase.update(id, body);
    }
}
