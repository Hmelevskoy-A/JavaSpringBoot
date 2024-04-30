package com.example.practiceteach.model;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.Set;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    private String name;

}

