package com.example.practiceteach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "task_task")
public class Task  {
    @Id
    private Long id;
    private String discriptionStatus;
    private Timestamp createdAt;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;
    public Task() {
    }

    public Task(Long id, String discriptionStatus, Timestamp createdAt, User user) {
        this.id = id;
        this.discriptionStatus = discriptionStatus;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscriptionStatus() {
        return discriptionStatus;
    }

    public void setDiscriptionStatus(String discriptionStatus) {
        this.discriptionStatus = discriptionStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
