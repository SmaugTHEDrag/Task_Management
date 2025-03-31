package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.TaskHistoryDTO;
import com.example.TaskManagementSystem.entity.TaskHistory;
import com.example.TaskManagementSystem.service.ITaskHistoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/histories")
public class TaskHistoryController {
    @Autowired
    private ITaskHistoryService taskHistoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<TaskHistoryDTO> getAllHistories(){
        List<TaskHistory> taskHistories = taskHistoryService.getAllHistories();
        return modelMapper.map(taskHistories, new TypeToken<List<TaskHistoryDTO>>(){}.getType());
    }
}
