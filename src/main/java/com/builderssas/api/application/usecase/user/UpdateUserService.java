package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.User;
import com.builderssas.api.domain.port.in.user.UpdateUserUseCase;
import com.builderssas.api.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository repository;

    @Override
    public Mono<User> update(Long id, User command) {
        User updated = new User(
            id,
            username, displayName, email, active
        );
        return repository.save(updated);
    }
}
