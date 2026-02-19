package com.backenEDS.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {

    /** Maneja validaciones @Valid */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Validación fallida");
        problem.setDetail(errors.toString());
        problem.setProperty("timestamp", Instant.now());

        return new ResponseEntity<>(problem, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /** Maneja recursos no encontrados */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Recurso no encontrado");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", Instant.now());

        return new ResponseEntity<>(problem, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /** Maneja errores de negocio u otros */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgs(IllegalArgumentException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Regla de negocio violada");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", Instant.now());

        return new ResponseEntity<>(problem, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /** Maneja cualquier otra excepción */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Error interno del servidor");
        problem.setDetail(ex.getMessage());
        problem.setProperty("timestamp", Instant.now());

        return new ResponseEntity<>(problem, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
