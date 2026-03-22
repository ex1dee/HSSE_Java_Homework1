package com.mipt.rezchikovsergey.sem2.spring_mvp.config.props;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(String name, String version, Storage storage) {
  public record Storage(Directories directories) {
    public record Directories(Path taskAttachments) {}
  }
}
