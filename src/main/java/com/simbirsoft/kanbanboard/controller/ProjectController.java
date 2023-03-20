package com.simbirsoft.kanbanboard.controller;

import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.simbirsoft.kanbanboard.service.ProjectService;
import com.simbirsoft.kanbanboard.model.Project;


@Controller
@RequestMapping("/")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  public String showAllProjects(Model model) {
    model.addAttribute("project", projectService.getAllProjects());
    return "project/index";
  }

  @GetMapping("/create")
  public String createProject(Model model) {
    model.addAttribute("project", new Project());
    return "project/create";
  }

  @PostMapping("/create")
  public String createProject(@ModelAttribute("project") @Valid Project project) {
    projectService.createProject(project.getTitle());
    return "redirect:/";
  }

  @GetMapping("/{id}/edit")
  public String editProject(@PathVariable Long id, Model model) {
    model.addAttribute("project", projectService.getProjectById(id)
        .orElseThrow(() -> new IllegalArgumentException("Недопустимый id проекта:" + id)));
    return "project/edit";
  }

  @PostMapping("/{id}/edit")
  public String editProject(@PathVariable Long id, @ModelAttribute("project") Project project) {
    project.setId(id);
    projectService.updateProject(project);
    return "redirect:/";
  }

  @GetMapping("/{id}/delete")
  public String deleteProject(@PathVariable Long id) {
    projectService.deleteProjectById(id);
    return "redirect:/";
  }
}
