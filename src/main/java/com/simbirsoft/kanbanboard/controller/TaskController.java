package com.simbirsoft.kanbanboard.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.simbirsoft.kanbanboard.model.*;
import com.simbirsoft.kanbanboard.service.*;

@Controller
public class TaskController {

  private final TaskService taskService;
  private final ProjectService projectService;

  public TaskController(TaskService taskService, ProjectService projectService) {
    this.taskService = taskService;
    this.projectService = projectService;
  }

  @GetMapping("/{id}")
  public String viewTasksByProject(@PathVariable("id") Long projectId, Model model) {
    Project project = projectService.getProjectById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projectId));
    model.addAttribute("project", project);
    model.addAttribute("tasks", taskService.getTasksByProject(project));
    return "task/index";
  }

  @GetMapping("/{id}/create")
  public String createTask(@PathVariable("id") Long projectId, Model model) {
    Project project = projectService.getProjectById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projectId));
    model.addAttribute("project", project);
    model.addAttribute("task", new Task());
    return "task/create";
  }

  @PostMapping("/{id}/create")
  public String createTask(@PathVariable("id") Long projectId,
      @ModelAttribute("task") @Validated Task task) {
    task.setProject(projectService.getProjectById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projectId)));
    taskService.createTask(task);
    return "redirect:/" + projectId;
  }
}