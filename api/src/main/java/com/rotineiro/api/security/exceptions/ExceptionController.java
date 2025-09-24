package com.rotineiro.api.security.exceptions;

import com.rotineiro.api.utils.DefaultResponse;
import com.rotineiro.api.utils.MessageDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Null;
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

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<DefaultResponse<Void>> handleBadRequest(Exception exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        DefaultResponse.<Void>builder()
            .message(exception.getMessage())
            .build()
    );
  }

  @ExceptionHandler({UsernameNotFoundException.class, NotFoundException.class})
  public ResponseEntity<DefaultResponse<Void>> handleNotFound(Exception exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        DefaultResponse.<Void>builder()
            .message(exception.getMessage())
            .build()
    );
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<DefaultResponse<Void>> handleUnauthorized(Exception exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        DefaultResponse.<Void>builder()
            .message(exception.getMessage())
            .build()
    );
  }

  @ExceptionHandler(BlockedException.class)
  public ResponseEntity<DefaultResponse<Void>> handleBlocked(Exception exception) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        DefaultResponse.<Void>builder()
            .message(exception.getMessage())
            .build()
    );
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<DefaultResponse<Map<String, String>>> handleConstraintViolationError(ConstraintViolationException exception) {
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      errors.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        DefaultResponse.<Map<String, String>>builder()
            .message("Formato inválido para os dados recebidos")
            .result(errors)
            .build()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<DefaultResponse<Map<String, String>>> handleMethodArgumentNotValidError(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        DefaultResponse.<Map<String, String>>builder()
            .message("Formato inválido para os dados recebidos")
            .result(errors)
            .build()
    );
  }
}