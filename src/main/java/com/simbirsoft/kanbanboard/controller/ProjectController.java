package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.service.ProjectService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping
  public String showAllProjects(Model model) {
    List<Project> projects = projectService.getAllProjects();
    model.addAttribute("projects", projects);
    return "projects";
  }

  @GetMapping("/{id}")
  public String showProjectById(@PathVariable Long id, Model model) {
    model.addAttribute("project", projectService.getProjectById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id)));
    return "project";
  }

  @GetMapping("/new")
  public String showProjectForm(Model model) {
    model.addAttribute("project", new Project());
    return "project-form";
  }

  @PostMapping("/new")
  public String addProject(@ModelAttribute("project") Project project) {
    projectService.saveProject(project);
    return "redirect:/projects";
  }
}
