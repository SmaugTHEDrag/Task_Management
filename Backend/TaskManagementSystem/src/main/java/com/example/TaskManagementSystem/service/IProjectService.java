package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.Project;

import java.util.List;

public interface IProjectService {
    List<Project> getAllProjects();

    Project getProjectById(int id);
}
