package com.simbirsoft.kanbanboard.model;

import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "roles")
public record Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    Long id,

    @Column(name = "role_name", unique = true, nullable = false)
    String name,

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    Set<User> users
) {

}
