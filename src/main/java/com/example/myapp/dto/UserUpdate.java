package com.example.myapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdate {

    @Valid

    @Email(message = "invalid email address")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^\\+\\d{2} \\d{2} \\d{3} \\d{4}$", message = "Invalid phone number format")
    private String msidn;
    @Null
    @Size(min = 5, max = 20, message = "password must be between 5 and 20 characters")
    private String password;
}
