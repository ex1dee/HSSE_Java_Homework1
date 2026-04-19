package com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
@Schema(description = "Information about a file attachment")
public record AttachmentResponseDto(
    @Schema(
            description = "Unique identifier of the attachment",
            example = "a1b2c3d4-e5f6-7g8h-9i0j")
        UUID id,
    @Schema(description = "Original filename", example = "specification.pdf") String filename,
    @Schema(description = "File size in bytes", example = "1048576") long size,
    @Schema(description = "Upload timestamp", example = "2026-03-24T11:00:00")
        LocalDateTime uploadedAt) {}
