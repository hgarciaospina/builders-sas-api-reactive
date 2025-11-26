package com.builderssas.api.infrastructure.web.handler;

import com.builderssas.api.infrastructure.web.validation.FieldOrderProvider;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manejador global de excepciones para la API Builders-SAS (WebFlux).
 *
 * Responsabilidades:
 * - Convertir excepciones en respuestas JSON consistentes.
 * - Garantizar un único mensaje de error para validaciones.
 * - Asegurar que los mensajes no incluyan nombres de campos.
 * - Delegar el orden de validación a los DTOs mediante FieldOrderProvider.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Construye un cuerpo estándar de error para un mensaje único.
     */
    private Map<String, Object> body(HttpStatus status, String message) {
        return Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "timestamp", LocalDateTime.now().toString(),
                "message", Optional.ofNullable(message)
                        .filter(m -> !m.isBlank())
                        .orElse("Error interno del servidor")
        );
    }

    /**
     * Construye un cuerpo estándar de error cuando se desea consolidar
     * múltiples mensajes en una sola cadena.
     */
    private Map<String, Object> body(HttpStatus status, List<String> messages) {
        return Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "timestamp", LocalDateTime.now().toString(),
                "message", String.join(" | ", messages)
        );
    }

    // =========================================================================
    // 404 — Recurso no encontrado
    // =========================================================================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 409 — Recurso duplicado
    // =========================================================================
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 409 — Violaciones de integridad de BD
    // =========================================================================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(body(status, "Violación de integridad de datos."));
    }

    // =========================================================================
    // 400 — Validaciones de DTOs WebFlux (body)
    // =========================================================================
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleWebFluxValidation(WebExchangeBindException ex) {

        /**
         * Determina el orden de campos basado en el DTO:
         * - Si el DTO implementa FieldOrderProvider → usa fieldOrder().
         * - Si no → usa el orden natural de los errores de WebFlux.
         *
         * Esto garantiza:
         * - Un solo error.
         * - En el orden exacto definido por el DTO.
         * - Sin mostrar el nombre del atributo.
         */
        var order = Optional.ofNullable(ex.getTarget())
                .filter(FieldOrderProvider.class::isInstance)
                .map(FieldOrderProvider.class::cast)
                .map(FieldOrderProvider::fieldOrder)
                .orElseGet(() ->
                        ex.getFieldErrors().stream()
                                .map(err -> err.getField())
                                .toList()
                );

        /**
         * Selecciona el primer mensaje de error según el orden declarado.
         * Si por alguna razón no existe ningún error (lo cual sería un fallo
         * de programación), se lanza una excepción explícita.
         */
        var firstError = order.stream()
                .flatMap(field ->
                        ex.getFieldErrors().stream()
                                .filter(err -> err.getField().equals(field))
                                .map(err -> err.getDefaultMessage())
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Error de validación inesperado.")
                );

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(body(status, firstError));
    }

    // =========================================================================
    // 400 — Validaciones de parámetros (@PathVariable, @RequestParam, etc.)
    // =========================================================================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {

        /**
         * Solo se toma la primera violación de restricción.
         * Nunca se muestra "Datos inválidos".
         */
        var error = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElseThrow(() ->
                        new RuntimeException("Error de validación inesperado.")
                );

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(body(status, error));
    }

    // =========================================================================
    // 401 — No autenticado
    // =========================================================================
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 403 — Prohibido (sin permisos)
    // =========================================================================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 422 — Errores de dominio (reglas de negocio)
    // =========================================================================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 500 — Errores no controlados
    // =========================================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    @ExceptionHandler(InvalidBusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidBusiness(InvalidBusinessRuleException ex) {
        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // Excepciones personalizadas
    // =========================================================================

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) { super(message); }
    }

    public static class DuplicateResourceException extends RuntimeException {
        public DuplicateResourceException(String message) { super(message); }
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) { super(message); }
    }

    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) { super(message); }
    }

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) { super(message); }
    }

    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) { super(message); }
    }

    public static class InvalidBusinessRuleException extends RuntimeException {
        public InvalidBusinessRuleException(String message) {
            super(message);
        }
    }
}
