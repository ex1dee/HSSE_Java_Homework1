package com.mipt.rezchikovsergey.sem2.spring_mvp.external.exceptions.handlers;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.handlers.BaseExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Глобальный обработчик исключений для REST-контроллеров. */
@RestControllerAdvice(basePackages = "com.mipt.rezchikovsergey.sem2.spring_mvp.external.controller")
public class GlobalExceptionHandler extends BaseExceptionHandler {}
