package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.service.ProjectService;
import com.simbirsoft.kanbanboard.service.ReleaseService;
import com.simbirsoft.kanbanboard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReleaseController {
  private final TaskService taskService;
  private final ProjectService projectService;
  private final ReleaseService releaseService;

  public ReleaseController(TaskService taskService, ProjectService projectService,
      ReleaseService releaseService) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.releaseService = releaseService;
  }

  @GetMapping("/{projId}/{taskId}")
  public String viewReleasesByTask(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      Model model
  ) {
    Task task = taskService.getTaskById(taskId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id задачи:" + taskId));
    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));
    model.addAttribute("project", project);
    model.addAttribute("task", taskService.getTasksByProject(project));
    model.addAttribute("release", releaseService.getReleasesByTask(task));
    return "release/index";
  }
}
