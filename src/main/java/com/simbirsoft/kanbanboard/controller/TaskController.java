package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.service.ProjectService;
import com.simbirsoft.kanbanboard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

  private final TaskService taskService;
  private final ProjectService projectService;

  public TaskController(TaskService taskService, ProjectService projectService) {
    this.taskService = taskService;
    this.projectService = projectService;
  }

  @GetMapping("/projects/{id}")
  public String showTasksByProjectId(@PathVariable Long id, Model model) {
    Project project = projectService.getProjectById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + id));
    List<Task> tasks = taskService.getTasksByProject(project);
    model.addAttribute("project", project);
    model.addAttribute("tasks", tasks);
    return "task/index";
  }
}