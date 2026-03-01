package com.mipt.rezchikovsergey.sem2.spring_mvp.config;

import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.StubTaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Конфигурационный класс для настройки бинов приложения. */
@Configuration
public class ApplicationConfig {
  @Bean
  public TaskRepository stubTaskRepository() {
    return new StubTaskRepository();
  }
}
