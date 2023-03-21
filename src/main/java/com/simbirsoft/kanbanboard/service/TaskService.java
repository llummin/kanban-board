package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.simbirsoft.kanbanboard.model.*;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public void createTask(Task task) {
    taskRepository.save(task);
  }

  public void deleteTaskById(Long id) {
    taskRepository.deleteById(id);
  }

  public List<Task> getTasksByProject(Project project) {
    return taskRepository.findByProject(project);
  }
}
