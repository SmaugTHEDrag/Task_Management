package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.Project;
import com.example.TaskManagementSystem.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService{
    @Autowired
    private IProjectRepository projectRepository;


    @Override
    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }
}
