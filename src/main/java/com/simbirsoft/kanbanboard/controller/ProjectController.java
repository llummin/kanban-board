package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.*;
import com.simbirsoft.kanbanboard.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  public String viewAllProjects(Model model) {
    model.addAttribute("projects", projectService.getAllProjects());
    return "project/index";
  }

  @GetMapping("/create")
  public String createProject(Model model) {
    model.addAttribute("project", new Project());
    return "project/create";
  }

  @PostMapping("/create")
  public String createProject(@ModelAttribute("project") @Valid Project project) {
    projectService.updateProject(project);
    return "redirect:/";
  }

  @GetMapping("/{id}/edit")
  public String editProject(@PathVariable("id") Long id, Model model) {
    model.addAttribute("project", projectService.getProjectById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта: " + id)));
    return "project/edit";
  }

  @PostMapping("/{id}/edit")
  public String editProject(@PathVariable Long id,
      @RequestParam(value = "isOpen", required = false) Boolean isOpen,
      @ModelAttribute("project") Project project) {
    Project existingProject = projectService.getProjectById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта: " + id));
    project.setIsOpen(isOpen != null ? isOpen : existingProject.getIsOpen());
    projectService.updateProject(project);
    return "redirect:/";
  }

  @GetMapping("/{id}/delete")
  public String closeProject(@PathVariable Long id) {
    projectService.closeProjectById(id);
    return "redirect:/";
  }
}
