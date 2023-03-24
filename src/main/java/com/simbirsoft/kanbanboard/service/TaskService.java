package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

/**
 * Сервис для работы с задачами.
 */
@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

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
    logger.debug("Вызван метод getTasksByProject с параметрами: project={}", project);
    List<Task> tasks = taskRepository.findByProject(project);
    logger.debug("Результат выполнения метода getTasksByProject: {}", tasks);
    return tasks;
  }

  /**
   * Получает задачу по его идентификатору.
   *
   * @param id Идентификатор задачи.
   * @return Задача с заданным идентификатором.
   */
  public Optional<Task> getTaskById(Long id) {
    logger.debug("Вызван метод getTaskById с параметрами: id={}", id);
    Optional<Task> task = taskRepository.findById(id);
    logger.debug("Результат выполнения метода getTaskById: {}", task);
    return task;
  }

  /**
   * Проверяет, принадлежит ли указанная задача проекту с указанным идентификатором проекта.
   *
   * @param task      Задача, подлежащая проверке.
   * @param projectId Идентификатор проекта, к которой должна принадлежать задача.
   * @throws IllegalArgumentException Если указанная задача не относится к указанному проекту.
   */
  public void checkTaskBelongsToProject(Task task, Long projectId) {
    logger.debug("Вызван метод checkTaskBelongsToProject с параметрами: task={}, projectId={}",
        task, projectId);
    if (!task.getProject().getId().equals(projectId)) {
      logger.error("Задача не принадлежит проекту");
      throw new IllegalArgumentException("Задача не принадлежит проекту");
    }
  }

  /**
   * Обновляет указанную задачу.
   *
   * @param task Задача, подлежащая обновлению.
   */
  public void updateTask(Task task) {
    logger.debug("Вызван метод updateTask с параметрами: task={}", task);
    taskRepository.save(task);
    logger.debug("Задача с id={} успешно обновлена", task.getId());
  }

  /**
   * Обновляет задачу, устанавливая название, автора, исполнителя и статуса в указанные значения.
   *
   * @param id        Идентификатор задачи, которая будет обновлена.
   * @param name      Новое название задачи.
   * @param author    Новый автор задачи.
   * @param performer Новый исполнитель задачи.
   * @param status    Новый статус задачи.
   */
  public void updateTask(Long id, String name, String author, String performer, String status) {
    logger.debug(
        "Вызван метод updateTask с параметрами id: {}, name: {}, author: {}, performer: {}, status: {}",
        id, name, author, performer, status);
    Task task = taskRepository.getReferenceById(id);
    task.setName(name);
    task.setAuthor(author);
    task.setPerformer(performer);
    task.setStatus(status);
    taskRepository.save(task);
    logger.debug("Задача с id {} успешно обновлена: {}", id, task);
  }

  /**
   * Удаляет задачу с указанным идентификатором.
   *
   * @param id Идентификатор задачи, которая будет удалена.
   */
  public void deleteTaskById(Long id) {
    logger.debug("Вызван метод deleteTaskById с параметром id: {}", id);
    taskRepository.deleteById(id);
    logger.debug("Задача с id={} удалена.", id);
  }
}
