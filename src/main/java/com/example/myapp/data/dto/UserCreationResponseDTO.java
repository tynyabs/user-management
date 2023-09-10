package com.example.myapp.data.dto;

public class UserCreationResponseDTO {
    private String message;

    public UserCreationResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}