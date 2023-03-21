package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.repository.TaskRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.simbirsoft.kanbanboard.model.*;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public void updateTask(Task task) {
    taskRepository.save(task);
  }

  public void deleteTaskById(Long id) {
    taskRepository.deleteById(id);
  }

  public List<Task> getTasksByProject(Project project) {
    return taskRepository.findByProject(project);
  }

  public Optional<Task> getTaskById(Long id) {
    return taskRepository.findById(id);
  }

  public void updateTask(Long id, String name, String author, String performer, String status) {
    Task task = taskRepository.getReferenceById(id);
    task.setName(name);
    task.setAuthor(author);
    task.setAuthor(performer);
    task.setStatus(status);
  }
}
