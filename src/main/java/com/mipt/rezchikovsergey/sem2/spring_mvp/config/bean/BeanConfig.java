package com.mipt.rezchikovsergey.sem2.spring_mvp.config.bean;

import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.impl.StubTaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Конфигурационный класс для настройки бинов приложения. */
@Configuration
public class BeanConfig {
  @Bean
  public TaskRepository stubTaskRepository() {
    return new StubTaskRepository();
  }
}
