package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.service.ProjectService;
import com.simbirsoft.kanbanboard.service.ReleaseService;
import com.simbirsoft.kanbanboard.service.TaskService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

  private final TaskService taskService;
  private final ProjectService projectService;
  private final ReleaseService releaseService;

  public TaskController(TaskService taskService, ProjectService projectService,
      ReleaseService releaseService) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.releaseService = releaseService;
  }

  @GetMapping("/{id}")
  public String viewTasksByProject(@PathVariable("id") Long projectId, Model model) {
    Project project = projectService.getProjectById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid project id:" + projectId));
    model.addAttribute("project", project);
    model.addAttribute("tasks", taskService.getTasksByProject(project));
    return "task/index";
  }

  @GetMapping("/{id}/create")
  public String createTask(@PathVariable("id") Long projectId, Model model) {
    Project project = projectService.getProjectById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid project id:" + projectId));
    model.addAttribute("project", project);
    model.addAttribute("task", new Task());
    model.addAttribute("release", new Release());
    return "task/create";
  }

  @PostMapping("/{projectId}/create")
  public String createTask(@PathVariable Long projectId, @RequestParam String name,
      @RequestParam String author, @RequestParam String performer,
      @RequestParam String version, @RequestParam LocalDateTime startDate,
      @RequestParam LocalDateTime endDate, String status) {

    // Получаем проект по id
    Optional<Project> optionalProject = projectService.getProjectById(projectId);
    if (optionalProject.isEmpty()) {
      return "redirect:/";
    }
    Project project = optionalProject.get();

    // Создаем задачу и устанавливаем связь с проектом
    Task task = new Task(name, author, performer, status);
    task.setProject(project);

    // Создаем релиз и устанавливаем связь с задачей
    Release release = new Release(version, startDate, endDate);
    release.setTask(task);

    // Сохраняем задачу и релиз в базу данных
    taskService.createTask(task);
    releaseService.createRelease(release);

    return "redirect:/" + projectId;
  }
}
