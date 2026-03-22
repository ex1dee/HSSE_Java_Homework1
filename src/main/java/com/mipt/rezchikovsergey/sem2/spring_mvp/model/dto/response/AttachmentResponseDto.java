package com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record AttachmentResponseDto(
    UUID id, String filename, long size, LocalDateTime uploadedAt) {}
