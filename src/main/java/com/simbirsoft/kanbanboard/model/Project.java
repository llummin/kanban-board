package com.simbirsoft.kanbanboard.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "proj_id")
  private Long id;

  @Column(name = "proj_title", nullable = false)
  private String title;

  @Column(name = "proj_is_open", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean isOpen = true;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<Task> tasks;

  public Project() {
  }

  public Project(Long id, String title, Boolean isOpen) {
    this.id = id;
    this.title = title;
    this.isOpen = isOpen;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getIsOpen() {
    return isOpen;
  }

  public void setIsOpen(Boolean isOpen) {
    this.isOpen = isOpen;
  }

  public List<Task> getTasks() {
    return tasks;
  }
}