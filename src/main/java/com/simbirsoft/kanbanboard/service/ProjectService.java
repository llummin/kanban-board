package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.ProjectRepository;
import com.simbirsoft.kanbanboard.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с проектами.
 */
@Service
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TaskRepository taskRepository;

  public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
    this.projectRepository = projectRepository;
    this.taskRepository = taskRepository;
  }

  /**
   * Получает все проекты.
   *
   * @return Список всех проектов.
   */
  public List<Project> getAllProjects() {
    return projectRepository.findAll();
  }

  /**
   * Получает проект по его идентификатору.
   *
   * @param id Идентификатор проекта.
   * @return Проект с заданным идентификатором
   */
  public Optional<Project> getProjectById(Long id) {
    return projectRepository.findById(id);
  }

  /**
   * Обновляет указанный проект.
   *
   * @param project Проект, подлежащий обновлению.
   */
  public void updateProject(Project project) {
    projectRepository.save(project);
  }

  /**
   * Закрывает проект с указанным идентификатором, установив для его флага isOpen значение false.
   *
   * @param id Идентификатор проекта, который будет закрыт.
   * @throws IllegalArgumentException Если указанный идентификатор не найден.
   * @throws IllegalStateException Если в проекте есть какие-либо невыполненные задачи.
   */
  public void closeProjectById(Long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + id));

    List<Task> tasks = taskRepository.findByProject(project);

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