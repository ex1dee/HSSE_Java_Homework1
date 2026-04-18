package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import static com.mipt.rezchikovsergey.sem2.spring_mvp.constants.ResilienceConstants.EXTERNAL_API_INSTANCE;

import com.mipt.rezchikovsergey.sem2.spring_mvp.client.ExternalAttachmentClient;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.AttachmentResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GatewayAttachmentService {
  private final ExternalAttachmentClient client;

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public AttachmentResponseDto storeAttachment(UUID taskId, MultipartFile file) {
    return client.storeAttachment(taskId, file);
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public ResponseEntity<Resource> downloadAttachment(UUID attachmentId) {
    return client.downloadAttachment(attachmentId);
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE, fallbackMethod = "fallbackGetAttachments")
  public List<AttachmentResponseDto> getTaskAttachments(UUID taskId) {
    return client.getTaskAttachments(taskId);
  }

  @RateLimiter(name = EXTERNAL_API_INSTANCE)
  @CircuitBreaker(name = EXTERNAL_API_INSTANCE)
  public void deleteAttachment(UUID attachmentId) {
    client.deleteAttachment(attachmentId);
  }

  private List<AttachmentResponseDto> fallbackGetAttachments(UUID taskId, Throwable t) {
    return Collections.emptyList();
  }
}
