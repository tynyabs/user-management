package com.example.myapp.dto;

import com.example.myapp.entity.userRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserCreate {

    @Valid

    @NotNull(message = "Name shouldn't be null")
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid Name format")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @NotNull(message = "Surname shouldn't be null")
    @Size(min = 3, max = 100, message = "Surname must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid Surname format")
    private String surname;

    @NotNull(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    @Email(message = "invalid email address")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address")
    private String email;

    @NotNull(message = "Msidn is mandatory")
    @NotBlank(message = "Msidn is mandatory")
    @Pattern(regexp = "^\\+\\d{2} \\d{2} \\d{3} \\d{4}$", message = "Invalid phone number format")
    private String msidn;

    @NotNull(message = "Msidn is mandatory")
    @NotBlank(message = "Msidn is mandatory")
    @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])\\d{4}[01][0-9][0-9]$",
            message = "Invalid identification number")
    private String idn;

    @NotNull(message = "password is mandatory")
    @NotBlank(message = "password is mandatory")
    @Size(min = 5, max = 20, message = "password must be between 5 and 20 characters")
    private String password;

    private String username;

    public userRole userRole;

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.username = email;
    }
}