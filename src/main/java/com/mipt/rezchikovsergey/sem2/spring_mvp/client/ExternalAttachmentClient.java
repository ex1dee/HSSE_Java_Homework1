package com.mipt.rezchikovsergey.sem2.spring_mvp.client;

import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.response.AttachmentResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ExternalAttachmentClient {
  private final RestClient externalClient;

  public AttachmentResponseDto storeAttachment(UUID taskId, MultipartFile file) {
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("file", file.getResource());

    MultiValueMap<String, HttpEntity<?>> multipartBody = builder.build();

    return externalClient
        .post()
        .uri("/external/v1/tasks/{taskId}/attachments", taskId)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(multipartBody)
        .retrieve()
        .body(AttachmentResponseDto.class);
  }

  public ResponseEntity<Resource> downloadAttachment(UUID attachmentId) {
    return externalClient
        .get()
        .uri("/external/v1/attachments/{attachmentId}", attachmentId)
        .accept(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL)
        .retrieve()
        .toEntity(Resource.class);
  }

  public void deleteAttachment(UUID attachmentId) {
    externalClient
        .delete()
        .uri("/external/v1/attachments/{attachmentId}", attachmentId)
        .retrieve()
        .toBodilessEntity();
  }

  public List<AttachmentResponseDto> getTaskAttachments(UUID taskId) {
    return externalClient
        .get()
        .uri("/external/v1/tasks/{taskId}/attachments", taskId)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(new ParameterizedTypeReference<List<AttachmentResponseDto>>() {});
  }
}
