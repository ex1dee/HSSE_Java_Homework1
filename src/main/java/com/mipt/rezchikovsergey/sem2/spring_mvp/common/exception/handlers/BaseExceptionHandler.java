package com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.handlers;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.BadRequestException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

public abstract class BaseExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ProblemDetail handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return createProblemDetail(
        HttpStatus.BAD_REQUEST,
        "Invalid parameters",
        "Validation failed",
        request,
        Map.of("errors", errors));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ProblemDetail handleConstraintViolation(
      ConstraintViolationException exception, HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getConstraintViolations()
        .forEach(
            violation -> errors.put(getField(violation.getPropertyPath()), violation.getMessage()));

    return createProblemDetail(
        HttpStatus.BAD_REQUEST,
        "Constraint Violation",
        "Invalid parameters in request path or query",
        request,
        Map.of("errors", errors));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  protected ProblemDetail handleMissingParameter(
      MissingServletRequestParameterException exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.BAD_REQUEST,
        "Bad Request",
        "Required parameter is missing: " + exception.getParameterName(),
        request,
        null);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ProblemDetail handleMessageNotReadable(
      HttpMessageNotReadableException exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.BAD_REQUEST, "Bad Request", "Malformed JSON request", request, null);
  }

  @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
  protected ProblemDetail handleNotFound(Exception exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.NOT_FOUND, "Not Found", exception.getMessage(), request, null);
  }

  @ExceptionHandler(BadRequestException.class)
  protected ProblemDetail handleBadRequest(
      BadRequestException exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.BAD_REQUEST, "Bad Request", exception.getMessage(), request, null);
  }

  @ExceptionHandler(Exception.class)
  protected ProblemDetail handleGeneralError(Exception exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal Server Error",
        exception.getMessage(),
        request,
        null);
  }

  protected ProblemDetail createProblemDetail(
      HttpStatus status,
      String title,
      String detail,
      HttpServletRequest request,
      Map<String, Object> properties) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
    problemDetail.setTitle(title);
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    if (properties != null) {
      problemDetail.setProperties(properties);
    }

    return problemDetail;
  }

  private String getField(Path path) {
    String field = null;

    for (Path.Node node : path) {
      field = node.getName();
    }

    return field;
  }
}
