package com.mipt.rezchikovsergey.sem2.spring_mvp.bpp;

import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TaskLifecycleProcessor implements BeanPostProcessor {
  private static final Logger log = LoggerFactory.getLogger(TaskLifecycleProcessor.class);

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (isTargetBean(bean)) {
      log.info("Bean '{}' was created", beanName);
    }

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (isTargetBean(bean)) {
      log.info("Bean '{}' was initialized", beanName);
    }

    return bean;
  }

  private boolean isTargetBean(Object bean) {
    return bean instanceof TaskService || bean instanceof TaskRepository;
  }
}
