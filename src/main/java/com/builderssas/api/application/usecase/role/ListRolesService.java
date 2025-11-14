package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.Role;
import com.builderssas.api.domain.port.in.role.ListRolesUseCase;
import com.builderssas.api.domain.port.out.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListRolesService implements ListRolesUseCase {

    private final RoleRepository repository;

    @Override
    public Flux<Role> listAll() {
        return repository.findAll();
    }
}
