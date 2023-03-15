package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "proj_id")
  private Long id;

  @Column(name = "proj_name", nullable = false)
  private String name;

  @Column(name = "proj_start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "proj_end_date")
  private LocalDateTime endDate;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<Task> tasks;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<Release> releases;

  public Project() {
  }

  public Project(String name, LocalDateTime startDate, LocalDateTime endDate) {
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public List<Release> getReleases() {
    return releases;
  }

  public void setReleases(List<Release> releases) {
    this.releases = releases;
  }
}