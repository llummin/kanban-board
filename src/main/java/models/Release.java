package models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "releases")
public class Release {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rls_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @Column(name = "rls_start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "rls_end_date", nullable = false)
  private LocalDateTime endDate;

  public Release() {
  }

  public Release(Project project, LocalDateTime startDate, LocalDateTime endDate) {
    this.project = project;
    this.startDate = startDate;
    this.endDate = endDate;
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
}