package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.*;
import com.simbirsoft.kanbanboard.service.*;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ReleaseController {

  private final ProjectService projectService;
  private final TaskService taskService;
  private final ReleaseService releaseService;

  public ReleaseController(
      ProjectService projectService,
      TaskService taskService,
      ReleaseService releaseService
  ) {
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
    Optional<Project> optionalProject = projectService.getProjectById(projId);
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalProject.isPresent() && optionalTask.isPresent()) {
      Project project = optionalProject.get();
      Task task = optionalTask.get();
      taskService.checkTaskBelongsToProject(task, project.getId());
      model.addAttribute("project", project);
      model.addAttribute("task", task);
      model.addAttribute("releases", releaseService.getReleasesByTask(task));
      return "release/index";
    } else {
      return "error";
    }
  }

  @GetMapping("/{projId}/{taskId}/create")
  public String addRelease(
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
      model.addAttribute("release", new Release());
      return "release/create";
    } else {
      return "error";
    }
  }

  @PostMapping("/{projId}/{taskId}/create")
  public String addRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      Release release
  ) {
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    if (optionalTask.isPresent()) {
      Task task = optionalTask.get();
      taskService.checkTaskBelongsToProject(task, projId);
      release.setTask(task);
      releaseService.updateRelease(release);
      return String.format("redirect:/%d/%d", projId, taskId);
    } else {
      return "error";
    }
  }

  @GetMapping("/{projId}/{taskId}/{rlsId}/edit")
  public String editRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @PathVariable("rlsId") Long rlsId,
      Model model
  ) {
    Optional<Project> optionalProject = projectService.getProjectById(projId);
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    Optional<Release> optionalRelease = releaseService.getReleaseById(rlsId);
    if (optionalProject.isPresent() && optionalTask.isPresent() && optionalRelease.isPresent()) {
      Project project = optionalProject.get();
      Task task = optionalTask.get();
      Release release = optionalRelease.get();
      taskService.checkTaskBelongsToProject(task, projId);
      releaseService.checkReleaseBelongsToTask(release, taskId);
      model.addAttribute("project", project);
      model.addAttribute("task", task);
      model.addAttribute("release", release);
      return "release/edit";
    } else {
      return "error";
    }
  }

  @PostMapping("/{projId}/{taskId}/{rlsId}/edit")
  public String editRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @PathVariable("rlsId") Long rlsId,
      @RequestParam String version,
      @RequestParam LocalDateTime startDate,
      @RequestParam LocalDateTime endDate
  ) {
    Optional<Project> optionalProject = projectService.getProjectById(projId);
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    Optional<Release> optionalRelease = releaseService.getReleaseById(rlsId);
    if (optionalProject.isPresent() && optionalTask.isPresent() && optionalRelease.isPresent()) {
      Task task = optionalTask.get();
      Release release = optionalRelease.get();
      taskService.checkTaskBelongsToProject(task, projId);
      releaseService.checkReleaseBelongsToTask(release, taskId);
      releaseService.checkReleaseBelongsToProject(release, projId);
      releaseService.updateRelease(rlsId, version, startDate, endDate);
      return String.format("redirect:/%d/%d", projId, taskId);
    } else {
      return "error";
    }
  }

  @GetMapping("/{projId}/{taskId}/{rlsId}/delete")
  public String deleteRelease(
      @PathVariable("projId") Long projId,
      @PathVariable("taskId") Long taskId,
      @PathVariable("rlsId") Long rlsId
  ) {
    Optional<Project> optionalProject = projectService.getProjectById(projId);
    Optional<Task> optionalTask = taskService.getTaskById(taskId);
    Optional<Release> optionalRelease = releaseService.getReleaseById(rlsId);
    if (optionalProject.isPresent() && optionalTask.isPresent() && optionalRelease.isPresent()) {
      Release release = optionalRelease.get();
      taskService.checkTaskBelongsToProject(release.getTask(), taskId);
      releaseService.checkReleaseBelongsToTask(release, taskId);
      releaseService.checkReleaseBelongsToProject(release, projId);
      releaseService.deleteReleaseById(rlsId);
      return String.format("redirect:/%d/%d", projId, taskId);
    } else {
      return "error";
    }
  }
}