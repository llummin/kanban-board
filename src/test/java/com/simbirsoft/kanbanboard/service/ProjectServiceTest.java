package com.simbirsoft.kanbanboard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.repository.ProjectRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private ProjectService projectService;

  private Project testProject;

  @BeforeEach
  void setUp() {
    LocalDateTime startDate = LocalDateTime.now();
    LocalDateTime endDate = startDate.plusDays(30);
    testProject = new Project("Test Project", startDate, endDate);
  }

  @Test
  void testGetAllProjects() {
    Project project1 = new Project("Project 1", LocalDateTime.now(), LocalDateTime.now());
    Project project2 = new Project("Project 2", LocalDateTime.now(), LocalDateTime.now());
    List<Project> projects = Arrays.asList(project1, project2);
    when(projectRepository.findAll()).thenReturn(projects);

    List<Project> allProjects = projectService.getAllProjects();

    assertEquals(2, allProjects.size());
    assertEquals("Project 1", allProjects.get(0).getName());
    assertEquals("Project 2", allProjects.get(1).getName());
  }

  @Test
  void testGetProjectById() {
    when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));

    Optional<Project> projectById = projectService.getProjectById(1L);

    assertTrue(projectById.isPresent());
    assertEquals("Test Project", projectById.get().getName());
  }

  @Test
  void testGetProjectByIdReturnsEmptyOptional() {
    when(projectRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<Project> projectById = projectService.getProjectById(1L);

    assertTrue(projectById.isEmpty());
  }

  @Test
  void testSaveProject() {
    when(projectRepository.save(any(Project.class))).thenReturn(testProject);

    Project savedProject = projectService.saveProject(testProject);

    assertNotNull(savedProject);
    assertEquals("Test Project", savedProject.getName());
  }

  @Test
  void testDeleteProjectById() {
    projectService.deleteProjectById(1L);

    verify(projectRepository, times(1)).deleteById(1L);
  }
}
