package com.builderssas.api.infrastructure.web.handler;

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

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    private Map<String, Object> body(HttpStatus status, List<String> messages) {
        return Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "timestamp", LocalDateTime.now().toString(),
                "message", String.join(" | ", messages)
        );
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // 409
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(body(status, "Violación de integridad de datos."));
    }

    // 400 — VALIDACIONES WEBFLUX (orden de atributos y SIN nombre de campo)
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleWebFluxValidation(WebExchangeBindException ex) {

        // Orden de los campos según tu DTO
        List<String> fieldOrder = List.of("name", "description", "active");

        // Obtener SOLO el primer mensaje según el orden, SIN mostrar el nombre del campo
        var firstError = fieldOrder.stream()
                .flatMap(field ->
                        ex.getFieldErrors().stream()
                                .filter(err -> err.getField().equals(field))
                                .map(err -> err.getDefaultMessage()) // <-- AQUÍ SE QUITA EL NOMBRE DEL CAMPO
                )
                .findFirst()
                .orElse("Datos inválidos");

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(body(status, firstError));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {

        var error = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(v -> v.getMessage()) // <-- TAMBIÉN SIN NOMBRE DEL CAMPO
                .orElse("Datos inválidos");

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(body(status, error));
    }

    // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // 403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(ForbiddenException ex) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // 422
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(body(status, ex.getMessage()));
    }

    // Excepciones personalizadas
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
}
