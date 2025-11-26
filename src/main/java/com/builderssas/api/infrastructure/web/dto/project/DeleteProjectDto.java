package com.builderssas.api.infrastructure.web.dto.project;

import jakarta.validation.constraints.NotNull;

public record DeleteProjectDto(

        @NotNull(message = "El id del proyecto es obligatorio.")
        Long id
) {}
