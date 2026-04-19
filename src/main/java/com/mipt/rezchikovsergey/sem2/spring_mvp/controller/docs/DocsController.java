package com.mipt.rezchikovsergey.sem2.spring_mvp.controller.docs;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/docs")
public class DocsController {
  @GetMapping
  @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
  public ResponseEntity<Map<String, String>> getInternalDocs() {
    return ResponseEntity.ok(Map.of("This is", "a stub"));
  }
}
