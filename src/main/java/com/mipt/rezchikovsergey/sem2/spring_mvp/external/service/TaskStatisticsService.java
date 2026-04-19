package com.mipt.rezchikovsergey.sem2.spring_mvp.external.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.external.repository.TaskRepository;
import com.mipt.rezchikovsergey.sem2.spring_mvp.common.model.dto.TaskPriorityCountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskStatisticsService {
  private final TaskRepository taskRepository;

  @Transactional(readOnly = true)
  public List<TaskPriorityCountDto> getTasksCountByPriority() {
    return taskRepository.getTasksCountByPriority();
  }
}
