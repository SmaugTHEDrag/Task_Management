package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectRepository extends JpaRepository<Project,Integer> {
}
