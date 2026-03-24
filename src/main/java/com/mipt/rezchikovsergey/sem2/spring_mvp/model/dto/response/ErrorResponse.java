package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path,
    Map<String, String> details) {}
