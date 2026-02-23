package com.mipt.rezchikovsergey.sem2.spring_mvp.context;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {
  private UUID requestId;
  private LocalDateTime startHandleTime;

  @PostConstruct
  public void init() {
    requestId = UUID.randomUUID();
    startHandleTime = LocalDateTime.now();
  }

  public UUID getRequestId() {
    return requestId;
  }

  public LocalDateTime getStartHandleTime() {
    return startHandleTime;
  }
}
