package com.builderssas.api.application.usecase.user;

import com.builderssas.api.domain.model.user.UserRecord;
import com.builderssas.api.domain.port.in.user.UpdateUserUseCase;
import com.builderssas.api.domain.port.out.user.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepositoryPort repository;

    @Override
    public Mono<UserRecord> update(Long id, UserRecord command) {

        UserRecord updated = new UserRecord(
                id,
                command.username(),
                command.displayName(),
                command.email(),
                command.active()
        );

        return repository.save(updated);
    }
}
