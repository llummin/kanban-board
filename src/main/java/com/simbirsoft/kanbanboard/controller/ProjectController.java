package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Project;
import com.simbirsoft.kanbanboard.service.ProjectService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
