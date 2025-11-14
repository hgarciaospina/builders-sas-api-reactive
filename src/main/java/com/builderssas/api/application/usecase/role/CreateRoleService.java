package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.Role;
import com.builderssas.api.domain.port.in.role.CreateRoleUseCase;
import com.builderssas.api.domain.port.out.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepository repository;

    @Override
    public Mono<Role> create(Role command) {
        return repository.save(command);
    }
}
