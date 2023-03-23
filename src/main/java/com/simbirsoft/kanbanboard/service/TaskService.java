package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

/**
 * Сервис для работы с задачами.
 */
@Service
public class TaskService {

  private final TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  /**
   * Получает список задач, связанных с указанным проектом.
   *
   * @param project Проект, для получения задач.
   * @return Список задач, связанных с указанным проектом.
   */
  public List<Task> getTasksByProject(Project project) {
    return taskRepository.findByProject(project);
  }

  /**
   * Получает задачу по его идентификатору.
   *
   * @param id Идентификатор задачи.
   * @return Задача с заданным идентификатором.
   */
  public Optional<Task> getTaskById(Long id) {
    return taskRepository.findById(id);
  }

  /**
   * Проверяет, принадлежит ли указанная задача проекту с указанным идентификатором проекта.
   *
   * @param task Задача, подлежащая проверке.
   * @param projectId Идентификатор проекта, к которой должна принадлежать задача.
   * @throws IllegalArgumentException Если указанная задача не относится к указанному проекту.
   */
  public void checkTaskBelongsToProject(Task task, Long projectId) {
    if (!task.getProject().getId().equals(projectId)) {
      throw new IllegalArgumentException("Задача не принадлежит проекту");
    }
  }

  /**
   * Обновляет указанную задачу.
   *
   * @param task Задача, подлежащая обновлению.
   */
  public void updateTask(Task task) {
    taskRepository.save(task);
  }

  /**
   * Обновляет задачу, устанавливая название, автора, исполнителя и статуса в указанные значения.
   * @param id Идентификатор задачи, которая будет обновлена.
   * @param name Новое название задачи.
   * @param author Новый автор задачи.
   * @param performer Новый исполнитель задачи.
   * @param status Новый статус задачи.
   */
  public void updateTask(Long id, String name, String author, String performer, String status) {
    Task task = taskRepository.getReferenceById(id);
    task.setName(name);
    task.setAuthor(author);
    task.setPerformer(performer);
    task.setStatus(status);
    taskRepository.save(task);
  }

  /**
   * Удаляет задачу с указанным идентификатором.
   *
   * @param id Идентификатор задачи, которая будет удалена.
   */
  public void deleteTaskById(Long id) {
    taskRepository.deleteById(id);
  }
}
