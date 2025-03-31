package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User getUserById(int id);
}
