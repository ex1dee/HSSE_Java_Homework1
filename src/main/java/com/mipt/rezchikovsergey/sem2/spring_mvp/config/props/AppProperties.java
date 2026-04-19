package com.mipt.rezchikovsergey.sem2.spring_mvp.config.props;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
    Client client,
    Security security,
    Info info,
    Cors cors,
    Cookie cookie,
    Storage storage,
    Log log) {
  public record Client(
      int connectionTimeoutSec, int readTimeoutSec, String userAgent, String baseUrl, Log log) {
    public record Log(int maxErrorSnippetLength) {}
  }

  public record Security(Jwt jwt, String passwordPepper) {
    public record Jwt(String secretKey, long expirationMs) {}
  }

  public record Info(String name, String version, String description, Contact contact) {
    public record Contact(String name, String email, String url) {}
  }

  public record Cors(String[] allowedOrigins, int maxAge) {}

  public record Cookie(CookieData viewMode) {
    public record CookieData(String name, String defaultValue, int maxAge) {}
  }

  public record Storage(Directories directories) {
    public record Directories(Path taskAttachments) {}
  }

  public record Log(String traceIdHeader, String mdcKey) {}
}
