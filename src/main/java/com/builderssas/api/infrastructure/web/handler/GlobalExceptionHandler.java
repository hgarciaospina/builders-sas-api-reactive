package com.builderssas.api.infrastructure.web.handler;

import com.builderssas.api.application.exception.DataInconsistencyException;
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
    // 422 — Inconsistencia de datos (DataInconsistencyException)
    // =========================================================================
    @ExceptionHandler(DataInconsistencyException.class)
    public ResponseEntity<Map<String, Object>> handleDataInconsistency(DataInconsistencyException ex) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
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
    // 409 — Recurso duplicado (regla de dominio)
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
        return ResponseEntity.status(status).body(body(status,
                "Operación no permitida: violación de integridad de datos en la base de datos."
        ));
    }

    // =========================================================================
    // 400 — Validaciones de DTOs WebFlux (body)
    // =========================================================================
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleWebFluxValidation(WebExchangeBindException ex) {

        var order = Optional.ofNullable(ex.getTarget())
                .filter(FieldOrderProvider.class::isInstance)
                .map(FieldOrderProvider.class::cast)
                .map(FieldOrderProvider::fieldOrder)
                .orElseGet(() ->
                        ex.getFieldErrors().stream()
                                .map(err -> err.getField())
                                .toList()
                );

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
    // 400 — Validaciones de parámetros
    // =========================================================================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {

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
    // 403 — Prohibido
    // =========================================================================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 422 — Reglas de negocio
    // =========================================================================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // =========================================================================
    // 400 — Reglas inválidas
    // =========================================================================
    @ExceptionHandler(InvalidBusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidBusiness(InvalidBusinessRuleException ex) {
        var status = HttpStatus.BAD_REQUEST;
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