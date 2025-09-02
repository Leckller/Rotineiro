package com.rotineiro.api.security.exceptions;

import com.rotineiro.api.utils.MessageDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<MessageDto> handleBadRequest(Exception exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new com.rotineiro.api.utils.MessageDto(exception.getMessage())
    );
  }

  @ExceptionHandler({UsernameNotFoundException.class, NotFoundException.class })
  public ResponseEntity<MessageDto> handleNotFound(Exception exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new MessageDto(exception.getMessage())
    );
  }

  @ExceptionHandler({BlockedException.class })
  public ResponseEntity<MessageDto> handleBlocked(Exception exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        new MessageDto(exception.getMessage())
    );
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolationError(ConstraintViolationException exception) {
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      errors.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidError(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

}