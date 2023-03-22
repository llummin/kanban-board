package com.simbirsoft.kanbanboard.model;

import jakarta.persistence.*;

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

  public Project() {
  }

  public Project(String title, Boolean isOpen) {
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
}