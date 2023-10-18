package com.example.myapp.controller;

import com.example.myapp.dto.*;
import com.example.myapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping
public class UserController {
    private final UserService userService;

    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequests userLoginRequests) throws Exception {
            return  userService.loginService(userLoginRequests.getUsername(),userLoginRequests.getPassword());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UserCreationResponse createUser(@Valid @RequestBody UserCreate userCreate) {
        return userService.createUser(userCreate);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/viewUser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/editUser/{id}", method = RequestMethod.POST)
    public UserCreationResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdate userUpdate) {
        return userService.updateUser(id, userUpdate);

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public UserCreationResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }

        return errors;
    }
}