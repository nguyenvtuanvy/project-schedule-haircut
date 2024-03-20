package com.example.projectschedulehaircutserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(mappedBy = "role")
    private Account account;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Manager> managers = new HashSet<>();
}
