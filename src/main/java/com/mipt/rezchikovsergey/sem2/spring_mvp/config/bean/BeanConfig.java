package com.mipt.rezchikovsergey.sem2.spring_mvp.config.bean;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.impl.StubTaskRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Конфигурационный класс для настройки бинов приложения. */
@Configuration
public class BeanConfig {
  @Bean
  public TaskRepository stubTaskRepository() {
    return new StubTaskRepository();
  }

  @Bean
  public OpenAPI openAPI(AppProperties appProperties) {
    AppProperties.Info info = appProperties.info();
    AppProperties.Info.Contact contact = info.contact();

    return new OpenAPI()
        .info(
            new Info()
                .title(info.name())
                .version(info.version())
                .description(info.description())
                .contact(
                    new Contact().name(contact.name()).email(contact.email()).url(contact.url())));
  }
}
