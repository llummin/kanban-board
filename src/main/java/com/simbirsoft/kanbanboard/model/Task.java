package com.simbirsoft.kanbanboard.model;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public record Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    Long id,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project,

    @Column(name = "task_name", nullable = false)
    String name,

    @Column(name = "task_author", nullable = false)
    String author,

    @Column(name = "task_performer", nullable = false)
    String performer,

    @Column(name = "task_release_version", nullable = false)
    String releaseVersion,

    @Column(name = "task_status", nullable = false)
    String status
) {

}
