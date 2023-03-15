package com.simbirsoft.kanbanboard.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public record User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    Role role,

    @Column(name = "user_username")
    String username,

    @Column(name = "user_password")
    String password,

    @Column(name = "user_email")
    String email
) {

}