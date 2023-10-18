package com.example.myapp.dto;

public record User(
        Long id,
        String name,
        String surname,
        String username,
        com.example.myapp.entity.userRole userRoles
)
{}