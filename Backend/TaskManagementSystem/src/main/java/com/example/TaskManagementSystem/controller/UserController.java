package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.dto.UserDTO;
import com.example.TaskManagementSystem.entity.User;
import com.example.TaskManagementSystem.service.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserDTO> getAllUser(){
        List<User> users = userService.getAllUser();
        return modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
    }
}

