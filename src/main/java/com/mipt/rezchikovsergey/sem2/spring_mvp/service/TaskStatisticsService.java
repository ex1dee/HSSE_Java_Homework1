package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.model.dto.TaskPriorityCountDto;
import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
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
