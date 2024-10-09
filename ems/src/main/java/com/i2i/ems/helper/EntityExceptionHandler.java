package com.i2i.ems.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * This class handles exceptions that may occur in REST controllers and provides appropriate HTTP responses.
 */
@RestControllerAdvice
public class EntityExceptionHandler {

    /**
     * <p>
     * Handles NoSuchElementException thrown when a requested element is not found.
     * </p>
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with a NOT_FOUND status and the exception message
     */
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * <p>
     * Handles MethodArgumentNotValidException thrown when validation on an argument fails.
     * </p>
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with a BAD_REQUEST status and a map of field errors
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * <p>
     * Handles custom exceptions defined in the application.
     * </p>
     *
     * @param e the custom exception that was thrown
     * @return a ResponseEntity with a BAD_REQUEST status and the exception message
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * <p>
     * Handles JwtAuthenticationException thrown during JWT authentication failures.
     * </p>
     *
     * @param e the exception that was thrown
     * @return a ResponseEntity with an UNAUTHORIZED status and the exception message
     */
    @ExceptionHandler(value = {JwtAuthenticationException.class})
    public ResponseEntity<String> handleJwtAuthenticationException(JwtAuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
