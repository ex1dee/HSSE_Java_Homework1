package com.mipt.rezchikovsergey.sem2.spring_mvp.external.controller;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/external/v1/unstable")
@RequiredArgsConstructor
public class UnstableController {
  private final AppProperties appProperties;

  @GetMapping
  public ResponseEntity<?> getUnstable(@RequestParam String mode) throws InterruptedException {
    log.info("Unstable endpoint called with mode: {}", mode);

    return switch (mode) {
      case "timeout" -> {
        int sleepTimeMs = 1000 * (appProperties.client().readTimeoutSec() + 1);
        Thread.sleep(sleepTimeMs);
        yield ResponseEntity.ok("Unstable endpoint called");
      }
      case "500" ->
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(
                  ProblemDetail.forStatusAndDetail(
                      HttpStatus.INTERNAL_SERVER_ERROR, "Simulated 500 error"));

      case "429" ->
          ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
              .header("Retry-After", "30")
              .body("Too many requests. Please wait 30 seconds.");

      case "html" ->
          ResponseEntity.status(HttpStatus.BAD_GATEWAY)
              .contentType(MediaType.TEXT_HTML)
              .body(
                  "<html><body><h1>502 Bad Gateway</h1><p>Nginx/Cloudflare mock error</p></body></html>");

      default -> ResponseEntity.ok("Stable. Mode: " + mode);
    };
  }
}
