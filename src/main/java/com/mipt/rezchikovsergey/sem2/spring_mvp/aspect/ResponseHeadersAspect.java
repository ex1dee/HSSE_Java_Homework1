package com.mipt.rezchikovsergey.sem2.spring_mvp.aspect;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseHeadersAspect {
  private final AppProperties appProperties;

  @Pointcut("execution(public org.springframework.http.ResponseEntity com.mipt..controller..*(..))")
  public void publicControllerMethod() {}

  @Around("publicControllerMethod()")
  public Object addHeaders(ProceedingJoinPoint joinPoint) throws Throwable {
    Object result = joinPoint.proceed();

    if (result instanceof ResponseEntity<?> response) {
      HttpHeaders headers = new HttpHeaders();
      headers.addAll(response.getHeaders());
      headers.add("X-API-Version", appProperties.version());

      return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());
    }

    return result;
  }
}
