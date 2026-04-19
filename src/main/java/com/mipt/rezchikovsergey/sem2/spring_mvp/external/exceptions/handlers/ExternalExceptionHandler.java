package com.mipt.rezchikovsergey.sem2.spring_mvp.external.exceptions.handlers;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.handlers.BaseExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.mipt.rezchikovsergey.sem2.spring_mvp.external.controller")
public class ExternalExceptionHandler extends BaseExceptionHandler {}
