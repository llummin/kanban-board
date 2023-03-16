package com.simbirsoft.kanbanboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @Column(name = "task_name", nullable = false)
  private String name;

  @Column(name = "task_author", nullable = false)
  private String author;

  @Column(name = "task_performer", nullable = false)
  private String performer;

  @Column(name = "task_release_version", nullable = false)
  private String releaseVersion;

  @Enumerated(EnumType.STRING)
  @Column(name = "task_status", nullable = false)
  private TaskStatus status;

  public Task() {

  }

  public Task(String name, Project project, String author, String performer, String releaseVersion,
      TaskStatus status) {
    this.project = project;
    this.name = name;
    this.author = author;
    this.performer = performer;
    this.releaseVersion = releaseVersion;
    this.status = status;
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

  public String getReleaseVersion() {
    return releaseVersion;
  }

  public void setReleaseVersion(String releaseVersion) {
    this.releaseVersion = releaseVersion;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  // Перечисление статуса задач
  public enum TaskStatus {
    BACKLOG,
    IN_PROGRESS,
    DONE
  }
}