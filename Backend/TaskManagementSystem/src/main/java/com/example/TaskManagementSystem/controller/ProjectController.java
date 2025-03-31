package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.ProjectDTO;
import com.example.TaskManagementSystem.entity.Project;
import com.example.TaskManagementSystem.service.IProjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/projects")
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProjectDTO> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        return modelMapper.map(projects, new TypeToken<List<ProjectDTO>>(){}.getType());
    }
}
