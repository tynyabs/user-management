package com.example.myapp.web;

import com.example.myapp.data.dto.User;
import com.example.myapp.data.dto.UserCreationResponseDTO;
import com.example.myapp.data.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserCreationResponseDTO createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserCreationResponseDTO updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id,user);

    }

    @DeleteMapping("/{id}")
    public UserCreationResponseDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}