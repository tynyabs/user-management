package com.example.myapp.dto;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<com.example.myapp.entity.User, User> {
    @Override
    public User apply(com.example.myapp.entity.User user) {
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getUserRole());
    }
}
