package com.builderssas.api.infrastructure.web.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

public record CreateProjectDto(

        @NotBlank(message = "El nombre del proyecto es obligatorio.")
        String name,

        @NotBlank(message = "La descripción del proyecto es obligatoria.")
        String description,

        @NotNull(message = "La fecha de inicio del proyecto es obligatoria.")
        @FutureOrPresent(message = "La fecha de inicio no puede ser anterior a hoy.")
        LocalDate startDate,

        String observations
) {}
