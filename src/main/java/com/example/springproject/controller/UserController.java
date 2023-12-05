package com.example.springproject.controller;

import com.example.springproject.response.UserResponse;
import com.example.springproject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        final List<UserResponse> users = service.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}

