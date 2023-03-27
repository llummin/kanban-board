package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

  private TaskService taskService;

  @Mock
  private TaskRepository taskRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    taskService = new TaskService(taskRepository);
  }

  @Test
  void getTasksByProject() {
    Project project = new Project();
    project.setId(1L);

    Task task1 = new Task();
    task1.setId(1L);
    task1.setProject(project);

    Task task2 = new Task();
    task2.setId(2L);
    task2.setProject(project);

    List<Task> expectedTasks = Arrays.asList(task1, task2);

    when(taskRepository.findByProject(project)).thenReturn(expectedTasks);

    List<Task> actualTasks = taskService.getTasksByProject(project);

    assertEquals(expectedTasks, actualTasks);

    verify(taskRepository, times(1)).findByProject(project);
  }

  @Test
  void getTaskById() {
    Task task = new Task();
    task.setId(1L);

    Optional<Task> expectedTask = Optional.of(task);

    when(taskRepository.findById(1L)).thenReturn(expectedTask);

    Optional<Task> actualTask = taskService.getTaskById(1L);

    assertEquals(expectedTask, actualTask);

    verify(taskRepository, times(1)).findById(1L);
  }

  @Test
  void checkTaskBelongsToProject() {
    Project project = new Project();
    project.setId(1L);

    Task task = new Task();
    task.setId(1L);
    task.setProject(project);

    assertDoesNotThrow(() -> taskService.checkTaskBelongsToProject(task, 1L));
    assertThrows(IllegalArgumentException.class,
        () -> taskService.checkTaskBelongsToProject(task, 2L));
  }

  @Test
  void updateTask() {
    Task task = new Task();
    task.setId(1L);

    taskService.updateTask(task);

    verify(taskRepository, times(1)).save(task);
  }

  @Test
  void updateTaskWithParams() {
    Task task = new Task();
    task.setId(1L);

    when(taskRepository.getReferenceById(1L)).thenReturn(task);

    String name = "новое имя";
    String author = "новый автор";
    String performer = "новый исполнитель";
    String status = "новый статус";

    taskService.updateTask(1L, name, author, performer, status);

    assertEquals(name, task.getName());
    assertEquals(author, task.getAuthor());
    assertEquals(performer, task.getPerformer());
    assertEquals(status, task.getStatus());

    verify(taskRepository, times(1)).save(task);
  }

  @Test
  void deleteTaskById() {
    // given
    Long taskId = 1L;
    Task taskToDelete = new Task();
    taskToDelete.setId(taskId);
    taskToDelete.setName("Задача к удалению");
    Project project = new Project();
    project.setId(1L);
    taskToDelete.setProject(project);
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskToDelete));

    taskService.deleteTaskById(taskId);

    verify(taskRepository, times(1)).deleteById(taskId);
  }
}
