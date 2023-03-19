package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Release;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.service.ProjectService;
import com.simbirsoft.kanbanboard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
  private final TaskService taskService;
  private final ProjectService projectService;

  public TaskController(TaskService taskService, ProjectService projectService) {
    this.taskService = taskService;
    this.projectService = projectService;
  }

  @GetMapping("/projects/{id}")
  public String viewTasksByProject(@PathVariable("id") Long projectId, Model model) {
    Project project = projectService.getProjectById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projectId));
    model.addAttribute("project", project);
    model.addAttribute("tasks", taskService.getTasksByProject(project));
    return "task/index";
  }
}