package com.builderssas.api.application.usecase.role;

import com.builderssas.api.domain.model.role.RoleRecord;
import com.builderssas.api.domain.port.in.role.GetRoleUseCase;
import com.builderssas.api.domain.port.out.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetRoleService implements GetRoleUseCase {

    private final RoleRepository repository;

    @Override
    public Mono<RoleRecord> getById(Long id) {
        return repository.findById(id);
    }
}
