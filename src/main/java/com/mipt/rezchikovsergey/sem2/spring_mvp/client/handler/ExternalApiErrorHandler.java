package com.mipt.rezchikovsergey.sem2.spring_mvp.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.base.BadRequestException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.exceptions.external.ExternalApiException;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.exception.task.TaskNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalApiErrorHandler implements ResponseErrorHandler {
  private final AppProperties appProperties;
  private final ObjectMapper objectMapper;

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return response.getStatusCode().isError();
  }

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse response)
      throws IOException {
    HttpStatusCode status = response.getStatusCode();
    MediaType contentType = response.getHeaders().getContentType();

    byte[] body = response.getBody().readAllBytes();

    if (contentType == null || !contentType.includes(MediaType.APPLICATION_JSON)) {
      handleNonJsonResponse(url, method, contentType, status, body);
    }

    ProblemDetail pd = parseProblemDetail(body, status);

    switch (status) {
      case HttpStatus.NOT_FOUND -> handleNotFound(pd);
      case HttpStatus.BAD_REQUEST -> handleBadRequest(pd);
      case HttpStatus.INTERNAL_SERVER_ERROR -> handleInternalError(pd);
      default -> throw new ExternalApiException(pd.getDetail(), status);
    }
  }

  private void handleNotFound(ProblemDetail pd) {
    throw new TaskNotFoundException(pd.getDetail());
  }

  private void handleBadRequest(ProblemDetail pd) {
    Map<String, Object> properties = pd.getProperties();

    if (properties != null && properties.containsKey("errors")) {
      throw new BadRequestException(pd.getDetail() + ": " + properties.get("errors"));
    }

    throw new BadRequestException(pd.getDetail());
  }

  private void handleInternalError(ProblemDetail pd) {
    log.error("External API 500 error: {}", pd.getDetail());

    throw new ExternalApiException(
        "External service internal error: " + pd.getDetail(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ProblemDetail parseProblemDetail(byte[] body, HttpStatusCode status) {
    try {
      return objectMapper.readValue(body, ProblemDetail.class);
    } catch (Exception e) {
      throw new ExternalApiException("Failed to parse ProblemDetail from external API", status);
    }
  }

  private void handleNonJsonResponse(
      URI url, HttpMethod method, MediaType contentType, HttpStatusCode status, byte[] body) {
    String bodySnippet =
        new String(
            body,
            0,
            Math.min(appProperties.client().log().maxErrorSnippetLength(), body.length),
            StandardCharsets.UTF_8);
    log.error(
        "Unexpected Content-Type: {} for {} {}. Body snippet: {}",
        contentType,
        method,
        url,
        bodySnippet);

    throw new ExternalApiException("Expected JSON but got " + contentType, status);
  }
}
