package com.builderssas.api.application.usecase.constructionrequest.coordinates;

import com.builderssas.api.domain.port.out.constructionorder.ConstructionOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Validates that the coordinates (latitude, longitude)
 * of a construction request are not already used by another order.
 *
 * Responsibilities:
 *  • Query the order repository for existing coordinates.
 *  • Return TRUE if coordinates are free.
 *  • Return FALSE if coordinates are already taken.
 *
 * Characteristics:
 *  • Pure application-level logic.
 *  • Immutable, fully reactive (Mono<Boolean>).
 *  • No business formatting or orchestration here.
 */
@Service
@RequiredArgsConstructor
public class CoordinateValidationService {

    private final ConstructionOrderRepositoryPort orderRepo;

    /**
     * Validates that no existing order occupies the given coordinates.
     *
     * @param latitude  request latitude
     * @param longitude request longitude
     * @return Mono<Boolean> → TRUE if coordinates are free, FALSE otherwise
     */
    public Mono<Boolean> validate(Double latitude, Double longitude) {

        return orderRepo.existsByLatitudeAndLongitude(latitude, longitude)
                .map(exists -> !exists);
    }
}
