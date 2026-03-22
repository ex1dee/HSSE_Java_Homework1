package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Глобальный обработчик исключений для REST-контроллеров. */
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<String> handleNotFound(TaskNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralError(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Internal server error: " + exception.getMessage());
  }
}
