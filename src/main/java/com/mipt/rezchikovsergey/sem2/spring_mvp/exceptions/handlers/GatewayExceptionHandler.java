package com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.handlers;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.handlers.BaseExceptionHandler;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.external.ExternalApiException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.mipt.rezchikovsergey.sem2.spring_mvp.controller")
public class GatewayExceptionHandler extends BaseExceptionHandler {
  @ExceptionHandler(ExternalApiException.class)
  public ProblemDetail handleExternalApiError(
      ExternalApiException exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.valueOf(exception.getStatus().value()),
        "External Service Error",
        exception.getMessage(),
        request,
        null);
  }

  @ExceptionHandler(CallNotPermittedException.class)
  public ProblemDetail handleCircuitBreaker(
      CallNotPermittedException exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.SERVICE_UNAVAILABLE,
        "Service Temporarily Unavailable",
        "Circuit breaker is open: " + exception.getCausingCircuitBreakerName(),
        request,
        null);
  }

  @ExceptionHandler(RequestNotPermitted.class)
  public ProblemDetail handleRateLimiter(
      RequestNotPermitted exception, HttpServletRequest request) {
    return createProblemDetail(
        HttpStatus.TOO_MANY_REQUESTS,
        "Too Many Requests",
        "Rate limit exceeded. Try again later.",
        request,
        null);
  }
}
