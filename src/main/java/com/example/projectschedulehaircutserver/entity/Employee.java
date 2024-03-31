package com.example.projectschedulehaircutserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends Account {
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private Set<Orders> orders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private Set<Comment> comments = new HashSet<>();
}
