package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Task implements ITaskService{
    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public List<com.example.TaskManagementSystem.entity.Task> getAllTask() {
        return taskRepository.findAll();
    }
}
