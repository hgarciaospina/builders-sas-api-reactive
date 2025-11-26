package com.builderssas.api.infrastructure.web.dto.user;

import com.builderssas.api.infrastructure.web.validation.FieldOrderProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO utilizado para actualizar usuarios existentes.
 *
 * Reglas:
 * - El ID debe venir en el body.
 * - Los demás campos siguen las mismas validaciones que en creación.
 * - Orden de validación garantizado por fieldOrder().
 */
public record UpdateUserDto(

        @NotNull(message = "El identificador del usuario es obligatorio.")
        Long id,

        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String username,

        @NotBlank(message = "El nombre visible es obligatorio.")
        String displayName,

        @NotBlank(message = "El correo es obligatorio.")
        @Email(message = "Debe proporcionar un correo electrónico válido.")
        String email,

        @NotNull(message = "Debe indicar si el usuario está activo o inactivo.")
        Boolean active

) implements FieldOrderProvider {

        @Override
        public List<String> fieldOrder() {
                return List.of("id", "username", "displayName", "email", "active");
        }
}
