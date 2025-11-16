package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.UpdateRoleUseCase;
import com.builderssas.api.domain.port.out.role.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateRoleService implements UpdateRoleUseCase {

    private final RoleRepositoryPort repository;

    @Override
    public Mono<RoleRecord> update(Long id, RoleRecord command) {

        RoleRecord updated = new RoleRecord(
                id,
                command.name(),
                command.description(),
                command.active()
        );

        return repository.save(updated);
    }
}
