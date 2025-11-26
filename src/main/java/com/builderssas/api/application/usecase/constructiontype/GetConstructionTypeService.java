package com.builderssas.api.application.usecase.constructiontype;

import com.builderssas.api.domain.model.constructiontype.ConstructionTypeRecord;
import com.builderssas.api.domain.port.in.constructiontype.GetConstructionTypeUseCase;
import com.builderssas.api.domain.port.out.constructiontype.ConstructionTypeRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Caso de uso para obtener un tipo de construcción por ID.
 *
 * Este caso de uso aplica la regla funcional:
 *
 *     "Solo devolver tipos de construcción activos"
 *
 * Si el registro existe pero está inactivo ⇒ se filtra y retorna Mono.empty().
 * Si existe y está activo ⇒ se retorna normalmente.
 *
 * No se modifica infraestructura ni repositorios.
 * No se agregan nuevas firmas a los puertos.
 * 100% funcional, sin lógica imperativa.
 */
@Service
@RequiredArgsConstructor
public class GetConstructionTypeService implements GetConstructionTypeUseCase {

    private final ConstructionTypeRepositoryPort repository;

    @Override
    public Mono<ConstructionTypeRecord> getById(Long id) {
        return repository.findById(id)
                .filter(ConstructionTypeRecord::active); // ← FILTRO DEFINITIVO Y ELEGANTE
    }
}
