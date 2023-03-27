package com.simbirsoft.kanbanboard.service;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.repository.ProjectRepository;
import com.simbirsoft.kanbanboard.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private TaskRepository taskRepository;

  private ProjectService projectService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    projectService = new ProjectService(projectRepository, taskRepository);
  }

  @Test
  public void testGetAllProjects() {
    List<Project> expectedProjects = new ArrayList<>();
    expectedProjects.add(new Project(1L, "Project 1", true));
    expectedProjects.add(new Project(2L, "Project 2", true));

    when(projectRepository.findAll()).thenReturn(expectedProjects);

    List<Project> actualProjects = projectService.getAllProjects();

    assertEquals(expectedProjects, actualProjects);

    verify(projectRepository, times(1)).findAll();
    verifyNoMoreInteractions(projectRepository);
  }

  @Test
  public void testGetProjectById() {
    Project expectedProject = new Project(1L, "Project 1", true);

    when(projectRepository.findById(1L)).thenReturn(Optional.of(expectedProject));

    Optional<Project> actualProject = projectService.getProjectById(1L);

    assertTrue(actualProject.isPresent());
    assertEquals(expectedProject, actualProject.get());

    verify(projectRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(projectRepository);
  }

  @Test
  public void testUpdateProject() {
    Project project = new Project(1L, "Project 1", true);

    projectService.updateProject(project);

    verify(projectRepository, times(1)).save(project);
    verifyNoMoreInteractions(projectRepository);
  }

  @Test
  public void testCloseProjectById() {
    Project project = new Project(1L, "Project 1", true);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    List<Task> tasks = new ArrayList<>();
    when(taskRepository.findByProject(project)).thenReturn(tasks);

    projectService.closeProjectById(1L);

    assertFalse(project.getIsOpen());
    verify(projectRepository, times(1)).findById(1L);
    verify(projectRepository, times(1)).save(project);
    verify(taskRepository, times(1)).findByProject(project);
    verifyNoMoreInteractions(projectRepository, taskRepository);
  }

  @Test
  public void testCloseProjectByIdThrowsIllegalArgumentException() {
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> {
      projectService.closeProjectById(1L);
    });

    verify(projectRepository, times(1)).findById(1L);
    verifyNoMoreInteractions(projectRepository, taskRepository);
  }

  @Test
  public void testCloseProjectByIdThrowsIllegalStateException() {
    Project project = new Project(1L, "Project 1", true);
    when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task(1L, "Task 1", "Author 1", "Performer 1", "BACKLOG", project));
    when(taskRepository.findByProject(project)).thenReturn(tasks);

    assertThrows(IllegalStateException.class, () -> {
      projectService.closeProjectById(1L);
    });

    assertTrue(project.getIsOpen());
    verify(projectRepository, times(1)).findById(1L);
    verify(taskRepository, times(1)).findByProject(project);
    verifyNoMoreInteractions(projectRepository, taskRepository);
  }
}