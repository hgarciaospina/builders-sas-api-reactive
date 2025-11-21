package com.builderssas.api.infrastructure.web.dto.user;

import com.builderssas.api.infrastructure.web.validation.FieldOrderProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * DTO para la creación de usuarios.
 * El usuario siempre se crea activo por defecto (active = true).
 */
public record CreateUserDto(

        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String username,

        @NotBlank(message = "El nombre visible es obligatorio.")
        String displayName,

        @NotBlank(message = "El correo es obligatorio.")
        @Email(message = "Debe proporcionar un correo electrónico válido.")
        String email

) implements FieldOrderProvider {

        @Override
        public List<String> fieldOrder() {
                return List.of("username", "displayName", "email");
        }
}
