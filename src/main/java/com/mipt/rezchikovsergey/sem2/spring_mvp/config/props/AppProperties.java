package com.mipt.rezchikovsergey.sem2.spring_mvp.config.props;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(Info info, Cors cors, Session session, Cookie cookie, Storage storage) {
  public record Info(String name, String version, String description, Contact contact) {
    public record Contact(String name, String email, String url) {}
  }

  public record Cors(String[] allowedOrigins, int maxAge) {}

  public record Session(Attributes attributes) {
    public record Attributes(String favoriteTasks) {}
  }

  public record Cookie(CookieData viewMode) {
    public record CookieData(String name, String defaultValue, int maxAge) {}
  }

  public record Storage(Directories directories) {
    public record Directories(Path taskAttachments) {}
  }
}
