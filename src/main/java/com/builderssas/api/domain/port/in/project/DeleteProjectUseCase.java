package com.builderssas.api.domain.port.in.project;

import reactor.core.publisher.Mono;

public interface DeleteProjectUseCase {
    Mono<Void> delete(Long id);
}
