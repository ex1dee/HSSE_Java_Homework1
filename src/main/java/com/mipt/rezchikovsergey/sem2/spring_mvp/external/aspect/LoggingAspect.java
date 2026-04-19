package com.mipt.rezchikovsergey.sem2.spring_mvp.external.aspect;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** Аспект для логирования работы методов сервисного слоя. */
@Aspect
@Component
public class LoggingAspect {

  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("execution(public * com.mipt.rezchikovsergey.sem2.spring_mvp.service.*.*(..))")
  public void servicePublicMethods() {}

  @Around("servicePublicMethods()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();

    log.info("Method {} called with arguments: {}", methodName, Arrays.toString(args));

    Object result;

    try {
      result = joinPoint.proceed();
    } catch (Throwable e) {
      log.error("Method {} thrown with exception: {}", methodName, e.getMessage());
      throw e;
    }

    log.info("Method {} finished with result: {}", methodName, result);

    return result;
  }
}
