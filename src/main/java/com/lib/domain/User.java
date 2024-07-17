package com.lib.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30 , nullable = false)
    private String firstName;

    @Column(length = 30 , nullable = false)
    private String lastName;

    @Column
    private Integer score;

    @Column
    private String address;

    @Column(length = 15 , nullable = false)
    private String phone;

    @Column
    private LocalDate birthDate;

    @Column(length = 80, nullable = false, unique = true)
    private String email;

    @Column(length = 120, nullable = false)
    private String password;

    @Column
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean builtIn = false;

    @ManyToMany
    @JoinTable(name = "t_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private List<Loans> loans = new ArrayList<>();


}
