package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.service.*;
import com.simbirsoft.kanbanboard.model.*;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class TaskController {

  private final ProjectService projectService;
  private final TaskService taskService;
  private final ReleaseService releaseService;

  public TaskController(
      TaskService taskService,
      ProjectService projectService,
      ReleaseService releaseService
  ) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.releaseService = releaseService;
  }

  @GetMapping("/{projId}")
  public String viewTasksByProject(@PathVariable("projId") Long projId, Model model) {
    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));
    model.addAttribute("project", project);
    model.addAttribute("tasks", taskService.getTasksByProject(project));
    return "task/index";
  }

  @GetMapping("/{projId}/create")
  public String createTask(@PathVariable("projId") Long projId, Model model) {
    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));
    model.addAttribute("project", project);
    model.addAttribute("task", new Task());
    model.addAttribute("release", new Release());
    return "task/create";
  }

  @PostMapping("/{projId}/create")
  public String createTask(
      @PathVariable("projId") Long projId,
      @ModelAttribute("task") Task task,
      @ModelAttribute("release") Release release
  ) {
    Project project = projectService.getProjectById(projId)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + projId));

    task.setProject(project);
    taskService.updateTask(task);
    release.setTask(task);
    releaseService.updateRelease(release);

    return "redirect:/" + projId;
  }


  @GetMapping("/{projId}/{taskId}/edit")
  public String editTask(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      Model model
  ) {
    Optional<Project> optionalProject = projectService.getProjectById(projId);
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalProject.isPresent() && optionalTask.isPresent()) {
      Project project = optionalProject.get();
      Task task = optionalTask.get();
      taskService.checkTaskBelongsToProject(task, project.getId());
      model.addAttribute("project", project);
      model.addAttribute("task", task);
      return "task/edit";
    } else {
      return "error";
    }
  }

  @PostMapping("/{projId}/{taskId}/edit")
  public String editTask(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @ModelAttribute("project") Project project,
      @ModelAttribute("release") Release release,
      @RequestParam String name,
      @RequestParam String author,
      @RequestParam String performer,
      @RequestParam String status
  ) {
    project.setId(projId);
    taskService.updateTask(taskId, name, author, performer, status);
    return "redirect:/" + projId;
  }

  @GetMapping("/{projId}/{taskId}/delete")
  public String deleteTask(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId
  ) {
    Optional<Project> optionalProject = projectService.getProjectById(projId);
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalProject.isPresent() && optionalTask.isPresent()) {
      Task task = optionalTask.get();
      taskService.checkTaskBelongsToProject(task, projId);
      taskService.deleteTaskById(taskId);
      return "redirect:/" + projId;
    } else {
      return "error";
    }
  }
}