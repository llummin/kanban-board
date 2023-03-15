package com.simbirsoft.kanbanboard.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projects")
public record Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_id")
    Long id,

    @Column(name = "proj_name", nullable = false)
    String name,

    @Column(name = "proj_start_date", nullable = false)
    LocalDateTime startDate,

    @Column(name = "proj_end_date")
    LocalDateTime endDate,

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    List<Task> tasks,

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    List<Release> releases
) {

}