package com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard error response object")
public record ErrorResponse(
    @Schema(description = "Timestamp of the error", example = "2026-03-24T10:15:30Z")
        Instant timestamp,
    @Schema(description = "HTTP status code", example = "400") int status,
    @Schema(description = "Generic error name", example = "Bad Request") String error,
    @Schema(
            description = "Detailed error message",
            example = "Validation failed for object 'taskCreateDto'")
        String message,
    @Schema(description = "API path where the error occurred", example = "/api/tasks") String path,
    @Schema(
            description = "Map of specific validation errors (field -> error message)",
            example = "{\"title\": \"must not be blank\"}")
        Map<String, String> details) {}
