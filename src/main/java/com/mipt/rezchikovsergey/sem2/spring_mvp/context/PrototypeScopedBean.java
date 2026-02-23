package com.mipt.rezchikovsergey.sem2.spring_mvp.context;

import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypeScopedBean {
  private final UUID id;

  public PrototypeScopedBean() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }
}
