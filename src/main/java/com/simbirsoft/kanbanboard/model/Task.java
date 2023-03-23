package com.simbirsoft.kanbanboard.model;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "proj_id")
  private Project project;

  @Column(name = "task_name")
  private String name;

  @Column(name = "task_author")
  private String author;

  @Column(name = "task_performer")
  private String performer;

  @Column(name = "task_status")
  private String status;

  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Release> releases;

  public Task() {
  }

  public Task(Long id, String name, String author, String performer, String status,
      Project project) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.performer = performer;
    this.status = status;
    this.project = project;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getPerformer() {
    return performer;
  }

  public void setPerformer(String performer) {
    this.performer = performer;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<Release> getReleases() {
    return releases;
  }
}