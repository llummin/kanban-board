package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.*;
import com.simbirsoft.kanbanboard.service.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ReleaseController {

  private final ProjectService projectService;
  private final TaskService taskService;
  private final ReleaseService releaseService;

  public ReleaseController(ProjectService projectService, TaskService taskService,
      ReleaseService releaseService) {
    this.projectService = projectService;
    this.taskService = taskService;
    this.releaseService = releaseService;
  }

  @GetMapping("/{projId}/{taskId}")
  public String viewReleasesByTask(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      Model model
  ) {
    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));
    Task task = taskService.getTaskById(taskId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id задачи:" + taskId));
    model.addAttribute("project", project);
    model.addAttribute("task", task);
    model.addAttribute("releases", releaseService.getReleasesByTask(task));
    return "release/index";
  }

  @GetMapping("/{projId}/{taskId}/create")
  public String addRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      Model model
  ) {
    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));
    Task task = taskService.getTaskById(taskId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id задачи:" + taskId));
    model.addAttribute("project", project);
    model.addAttribute("task", task);
    model.addAttribute("release", new Release());
    return "release/create";
  }

  @PostMapping("/{projId}/{taskId}/create")
  public String addRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @RequestParam String version,
      @RequestParam LocalDateTime startDate,
      @RequestParam LocalDateTime endDate
  ) {

    // Получаем задачу по id
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalTask.isEmpty()) {
      return "redirect:/";
    }
    Task task = optionalTask.get();

    // Создаем релиз и устанавливаем связь с задачей
    Release release = new Release(version, startDate, endDate);
    release.setTask(task);

    // Сохраняем релиз в базе данных
    releaseService.createRelease(release);

    return String.format("redirect:/%d/%d", projId, taskId);
  }
}
