package com.simbirsoft.kanbanboard.controller;

import com.simbirsoft.kanbanboard.model.Task;
import com.simbirsoft.kanbanboard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping
  public String getAllTasks(Model model) {
    List<Task> tasks = taskService.getAllTasks();
    model.addAttribute("tasks", tasks);
    return "tasks";
  }
}
