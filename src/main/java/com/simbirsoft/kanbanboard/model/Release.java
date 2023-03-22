package com.simbirsoft.kanbanboard.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "releases")
public class Release {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rls_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @Column(name = "rls_version")
  private String version;

  @Column(name = "rls_start_date")
  private LocalDateTime startDate;

  @Column(name = "rls_end_date")
  private LocalDateTime endDate;

  public Release() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public String getFormattedStartDate() {
    return getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
  }

  public String getFormattedEndDate() {
    return getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
  }
}