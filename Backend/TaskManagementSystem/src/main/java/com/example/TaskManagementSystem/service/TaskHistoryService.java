package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.TaskHistory;
import com.example.TaskManagementSystem.repository.ITaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryService implements ITaskHistoryService{
    @Autowired
    private ITaskHistoryRepository taskHistoryRepository;
    @Override
    public List<TaskHistory> getAllHistories() {
        return taskHistoryRepository.findAll();
    }
}
