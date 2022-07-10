package de.telran.cabas.controller;

import de.telran.cabas.dto.response.InputValidationResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InputValidationResponseDTO> handleError(MethodArgumentNotValidException ex) {
        var responseBody = ex.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                Collectors.toList()
                        )
                ));
        InputValidationResponseDTO response = InputValidationResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .errors(responseBody)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<InputValidationResponseDTO> handleConstraint(ConstraintViolationException ex) {
        String responseBody = ex.getMessage();
        InputValidationResponseDTO response = InputValidationResponseDTO
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(responseBody)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<InputValidationResponseDTO> handlePathVariableError(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(InputValidationResponseDTO
                        .builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getName() + ": " + ex.getErrorCode() + ", " + "check URL parameter!")
                        .build());
    }
}
