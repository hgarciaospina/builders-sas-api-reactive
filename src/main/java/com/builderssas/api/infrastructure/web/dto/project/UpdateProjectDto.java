package com.builderssas.api.infrastructure.web.dto.project;

import jakarta.validation.constraints.NotBlank;

public record UpdateProjectDto(

        @NotBlank(message = "El nombre del proyecto es obligatorio.")
        String name,

        @NotBlank(message = "La descripción del proyecto es obligatoria.")
        String description,

        @NotBlank(message = "Las observaciones son obligatorias.")
        String observations
) {}
