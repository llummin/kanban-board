package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getTasksByProject(Project project) {
    return taskRepository.findByProject(project);
  }

  public Optional<Task> getTaskById(Long id) {
    return taskRepository.findById(id);
  }

  public void checkTaskBelongsToProject(Task task, Long projectId) {
    if (!task.getProject().getId().equals(projectId)) {
      throw new IllegalArgumentException("Задача не принадлежит проекту");
    }
  }

  public void updateTask(Task task) {
    taskRepository.save(task);
  }

  public void updateTask(Long id, String name, String author, String performer, String status) {
    Task task = taskRepository.getReferenceById(id);
    task.setName(name);
    task.setAuthor(author);
    task.setPerformer(performer);
    task.setStatus(status);
    taskRepository.save(task);
  }

  public void deleteTaskById(Long id) {
    taskRepository.deleteById(id);
  }
}
