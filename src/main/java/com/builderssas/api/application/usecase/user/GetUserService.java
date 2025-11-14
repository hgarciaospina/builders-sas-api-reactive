package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.User;
import com.builderssas.api.domain.port.in.user.GetUserUseCase;
import com.builderssas.api.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final UserRepository repository;

    @Override
    public Mono<User> getById(Long id) {
        return repository.findById(id);
    }
}
