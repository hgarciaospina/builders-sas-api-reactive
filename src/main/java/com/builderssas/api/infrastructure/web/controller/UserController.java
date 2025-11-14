package com.builderssas.api.infrastructure.web.controller;

import com.builderssas.api.domain.model.user.User;
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
    public Flux<User> list() {
        return listUseCase.listAll();
    }

    @GetMapping("/<built-in function id>")
    public Mono<User> get(@PathVariable Long id) {
        return getUseCase.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> create(@RequestBody User body) {
        return createUseCase.create(body);
    }

    @PutMapping("/<built-in function id>")
    public Mono<User> update(@PathVariable Long id, @RequestBody User body) {
        return updateUseCase.update(id, body);
    }
}
