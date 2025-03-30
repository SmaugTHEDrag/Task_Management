package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Integer> {
}
