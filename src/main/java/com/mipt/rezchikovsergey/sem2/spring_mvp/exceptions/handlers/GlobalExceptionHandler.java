package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.handlers;

import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.BadRequestException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.base.NotFoundException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.Path.Node;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/** Глобальный обработчик исключений для REST-контроллеров. */
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    Map<String, String> details = new HashMap<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(error -> details.put(error.getField(), error.getDefaultMessage()));

    return buildResponse(
        HttpStatus.BAD_REQUEST, "Validation Failed", "Invalid parameters", request, details);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      ConstraintViolationException exception, HttpServletRequest request) {
    Map<String, String> details = new HashMap<>();
    exception
        .getConstraintViolations()
        .forEach(
            violation ->
                details.put(getField(violation.getPropertyPath()), violation.getMessage()));

    return buildResponse(
        HttpStatus.BAD_REQUEST, "Constraint Violation", "Invalid parameters", request, details);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParameter(
      MissingServletRequestParameterException ex, HttpServletRequest request) {
    return buildResponse(
        HttpStatus.BAD_REQUEST,
        "Bad Request",
        "Required parameter is missing: " + ex.getParameterName(),
        request,
        null);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleMessageNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    return buildResponse(
        HttpStatus.BAD_REQUEST, "Bad Request", "Malformed JSON request", request, null);
  }

  @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
  public ResponseEntity<ErrorResponse> handleNotFound(
      Exception exception, HttpServletRequest request) {
    return buildResponse(HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request, null);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(
      BadRequestException exception, HttpServletRequest request) {
    return buildResponse(
        HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage(), request, null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralError(
      Exception exception, HttpServletRequest request) {
    return buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal Server Error",
        exception.getMessage(),
        request,
        null);
  }

  private ResponseEntity<ErrorResponse> buildResponse(
      HttpStatus status,
      String error,
      String message,
      HttpServletRequest request,
      Map<String, String> details) {
    ErrorResponse response =
        new ErrorResponse(
            Instant.now(), status.value(), error, message, request.getRequestURI(), details);

    return ResponseEntity.status(status).body(response);
  }

  private String getField(Path path) {
    String field = null;

    for (Node node : path) {
      field = node.getName();
    }

    return field;
  }
}
