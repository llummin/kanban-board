package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.ProjectRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TaskService taskService;

  public ProjectService(ProjectRepository projectRepository, TaskService taskService) {
    this.projectRepository = projectRepository;
    this.taskService = taskService;
  }

  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  public Optional<Project> getProjectById(Long id) {
    return projectRepository.findById(id);
  }

  public void updateProject(Project project) {
    projectRepository.save(project);
  }

  public void closeProjectById(Long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + id));

    List<Task> tasks = taskService.getTasksByProject(project);

    boolean isBacklogOrInProgressTaskExist = tasks.stream()
        .anyMatch(t -> t.getStatus().equals("BACKLOG") || t.getStatus().equals("IN_PROGRESS"));

    if (isBacklogOrInProgressTaskExist) {
      throw new IllegalStateException(
          "Невозможно закрыть проект, так как есть невыполненные задачи");
    } else {
      project.setIsOpen(false);
      projectRepository.save(project);
    }
  }
}