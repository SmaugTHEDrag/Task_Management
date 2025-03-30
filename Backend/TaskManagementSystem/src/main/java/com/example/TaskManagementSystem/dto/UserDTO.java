package com.example.TaskManagementSystem.dto;

import com.example.TaskManagementSystem.entity.Role;
import com.example.TaskManagementSystem.entity.User;
import lombok.*;

import java.time.LocalDateTime;

public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String role;
    private boolean active;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}

