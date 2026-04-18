package com.mipt.rezchikovsergey.sem2.spring_mvp.config.bean;

import com.mipt.rezchikovsergey.sem2.spring_mvp.client.handler.ExternalApiErrorHandler;
import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
  @Bean
  public RestClient externalClient(AppProperties props, ExternalApiErrorHandler errorHandler) {
    JdkClientHttpRequestFactory factory = createFactory(props);
    factory.setReadTimeout(Duration.ofSeconds(props.client().readTimeoutSec()));

    return RestClient.builder()
        .requestFactory(factory)
        .baseUrl(props.client().baseUrl())
        .defaultHeader(HttpHeaders.USER_AGENT, props.client().userAgent())
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultStatusHandler(errorHandler)
        .build();
  }

  private JdkClientHttpRequestFactory createFactory(AppProperties props) {
    return new JdkClientHttpRequestFactory(
        HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(props.client().connectionTimeoutSec()))
            .build());
  }
}
