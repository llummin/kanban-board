package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.*;
import com.simbirsoft.kanbanboard.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.util.Optional;

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
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projectId));
    model.addAttribute("project", project);
    model.addAttribute("tasks", taskService.getTasksByProject(project));
    return "task/index";
  }

  @GetMapping("/{id}/create")
  public String createTask(@PathVariable("id") Long id, Model model) {
    Project project = projectService.getProjectById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + id));
    model.addAttribute("project", project);
    model.addAttribute("task", new Task());
    model.addAttribute("release", new Release());
    return "task/create";
  }

  @PostMapping("/{id}/create")
  public String createTask(
      @PathVariable("id") Long id,
      @RequestParam String name,
      @RequestParam String author,
      @RequestParam String performer,
      @RequestParam String status,
      @RequestParam String version,
      @RequestParam LocalDateTime startDate,
      @RequestParam LocalDateTime endDate
  ) {

    // Получаем проект по id
    Optional<Project> optionalProject = projectService.getProjectById(id);
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
    taskService.updateTask(task);
    releaseService.createRelease(release);

    return "redirect:/" + id;
  }

  @GetMapping("/{projId}/{taskId}/edit")
  public String editTask(
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
    return "task/edit";
  }

  @PostMapping("/{projId}/{taskId}/edit")
  public String editTask(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @RequestParam String name,
      @RequestParam String author,
      @RequestParam String performer,
      @RequestParam String status,
      @ModelAttribute("project") Project project,
      @ModelAttribute("release") Release release
  ) {
    // Получаем задачу по id
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalTask.isEmpty()) {
      return "redirect:/";
    }
    Task task = optionalTask.get();

    // Обновляем поля задачи
    task.setName(name);
    task.setAuthor(author);
    task.setPerformer(performer);
    task.setStatus(status);

    // Обновляем связь задачи с проектом
    project.setId(projId);
    task.setProject(project);

    // Сохраняем задачу и релиз в базу данных
    taskService.updateTask(task);

    return "redirect:/" + projId;
  }
}