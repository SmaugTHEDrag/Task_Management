package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.TaskDTO;
import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.service.ITaskService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    @Autowired
    private ITaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<TaskDTO> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return modelMapper.map(tasks, new TypeToken<List<TaskDTO>>(){}.getType());
    }

    @GetMapping("{id}")
    public TaskDTO getTaskById(@PathVariable int id){
        Task task = taskService.getTaskById(id);
        return modelMapper.map(task, TaskDTO.class);
    }
}
