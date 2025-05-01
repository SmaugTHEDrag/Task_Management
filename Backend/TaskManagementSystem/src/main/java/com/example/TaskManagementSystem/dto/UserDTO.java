package com.example.TaskManagementSystem.dto;

import com.example.TaskManagementSystem.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private String role;
    private boolean active;

    private List<TaskDTO> assignedTasks;
    private static class TaskDTO{
        private String title;
        private String description;
        private String status;
        private String priority;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }
    }
    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<TaskDTO> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<TaskDTO> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }
}

