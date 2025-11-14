package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.Role;
import com.builderssas.api.domain.port.in.role.UpdateRoleUseCase;
import com.builderssas.api.domain.port.out.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateRoleService implements UpdateRoleUseCase {

    private final RoleRepository repository;

    @Override
    public Mono<Role> update(Long id, Role command) {
        Role updated = new Role(
            id,
            name, description, active
        );
        return repository.save(updated);
    }
}
