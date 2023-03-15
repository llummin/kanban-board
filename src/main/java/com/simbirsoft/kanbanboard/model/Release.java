package com.simbirsoft.kanbanboard.model;

import javax.persistence.*;
import java.time.LocalDateTime;

public record Release(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rls_id")
    Long id,

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    Project project,

    @Column(name = "rls_start_date", nullable = false)
    LocalDateTime startDate,

    @Column(name = "rls_end_date", nullable = false)
    LocalDateTime endDate
) {

}
