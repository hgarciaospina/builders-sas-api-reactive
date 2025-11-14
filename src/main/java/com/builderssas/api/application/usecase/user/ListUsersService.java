package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.User;
import com.builderssas.api.domain.port.in.user.ListUsersUseCase;
import com.builderssas.api.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListUsersService implements ListUsersUseCase {

    private final UserRepository repository;

    @Override
    public Flux<User> listAll() {
        return repository.findAll();
    }
}
