package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.CreateUserUseCase;
import com.builderssas.api.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository repository;

    @Override
    public Mono<UserRecord> create(UserRecord command) {
        return repository.save(command);
    }
}
