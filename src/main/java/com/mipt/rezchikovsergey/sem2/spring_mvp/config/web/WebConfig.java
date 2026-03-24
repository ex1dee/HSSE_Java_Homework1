package com.mipt.rezchikovsergey.sem2.spring_mvp.config.web;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private final AppProperties appProperties;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/api/**")
        .allowedOrigins(appProperties.cors().allowedOrigins())
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("Authorization", "Content-Type")
        .exposedHeaders("X-Total-Count", "X-API-Version")
        .allowCredentials(true)
        .maxAge(appProperties.cors().maxAge());
  }
}
