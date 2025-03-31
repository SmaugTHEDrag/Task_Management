package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.Task;

import java.util.List;

public interface ITaskService {
    List<Task> getAllTasks();

    Task getTaskById(int id);
}
