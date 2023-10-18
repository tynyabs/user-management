package com.example.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginResponse{
    private Long id;
    private String Role;
    private String token;
    private String expiry;
    private String issuedAt;

}