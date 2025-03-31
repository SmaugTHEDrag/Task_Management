package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {

}
