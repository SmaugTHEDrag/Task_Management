package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<User> getAllUsers();

    User getUserById(int id);

}
