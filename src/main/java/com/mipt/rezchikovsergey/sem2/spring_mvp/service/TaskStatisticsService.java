package com.mipt.rezchikovsergey.sem2.spring_mvp.service;

import com.mipt.rezchikovsergey.sem2.spring_mvp.repository.TaskRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TaskStatisticsService {
  private final TaskRepository primaryTaskRepository;
  private final TaskRepository stubTaskRepository;

  public TaskStatisticsService(
      TaskRepository primaryTaskRepository,
      @Qualifier("stubTaskRepository") TaskRepository stubTaskRepository) {
    this.primaryTaskRepository = primaryTaskRepository;
    this.stubTaskRepository = stubTaskRepository;
  }

  public void compareRepositories() {
    System.out.println();
    System.out.println("Primary repo: " + primaryTaskRepository.getClass().getSimpleName());
    System.out.println("Stub repo: " + stubTaskRepository.getClass().getSimpleName());
    System.out.println("\n============================================\n");
    System.out.println("Finding unexisting task...");
    System.out.println("Found in primary: " + primaryTaskRepository.findById(UUID.randomUUID()));
    System.out.println("Found in stub: " + stubTaskRepository.findById(UUID.randomUUID()));
    System.out.println();
  }
}
