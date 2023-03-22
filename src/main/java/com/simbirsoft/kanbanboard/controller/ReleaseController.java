package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.*;
import com.simbirsoft.kanbanboard.service.*;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;
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
      Release release
  ) {

    // Получаем задачу по id
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalTask.isEmpty()) {
      return "redirect:/";
    }
    Task task = optionalTask.get();
    // Создаем релиз и устанавливаем связь с задачей
    release.setTask(task);
    // Сохраняем релиз в базе данных
    releaseService.createRelease(release);

    return String.format("redirect:/%d/%d", projId, taskId);
  }

  @GetMapping("/{projId}/{taskId}/{rlsId}/edit")
  public String editRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @PathVariable("rlsId") Long rlsId,
      Model model
  ) {

    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));
    Task task = taskService.getTaskById(taskId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id задачи:" + taskId));
    Release release = releaseService.getReleaseById(rlsId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id релиза:" + taskId));
    // Проверяем, принадлежит ли релиз текущей задаче
    if (!release.getTask().getId().equals(taskId)) {
      return "redirect:/error";
    }
    model.addAttribute("project", project);
    model.addAttribute("task", task);
    model.addAttribute("release", release);
    return "release/edit";
  }

  @PostMapping("/{projId}/{taskId}/{rlsId}/edit")
  public String editRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @PathVariable("rlsId") Long rlsId,
      @ModelAttribute("task") Task task,
      @RequestParam String version,
      @RequestParam LocalDateTime startDate,
      @RequestParam LocalDateTime endDate
  ) {

    // Получаем релиз по id
    Optional<Release> optionalRelease = releaseService.getReleaseById(rlsId);
    if (optionalRelease.isEmpty()) {
      return "redirect:/";
    }
    Release release = optionalRelease.get();

    // Обновляем связь релиза с задачей
    task.setId(taskId);
    release.setTask(task);

    // Сохраняем релиз в базу данных
    releaseService.updateRelease(rlsId, version, startDate, endDate);

    releaseService.updateRelease(release);
    return String.format("redirect:/%d/%d", projId, taskId);
  }
}