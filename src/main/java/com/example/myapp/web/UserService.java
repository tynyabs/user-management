package com.example.myapp.web;

import com.example.myapp.data.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public UserCreationResponseDTO createUser(User user) {
        Optional<User> optionalUserEmail = userRepository.findUserByEmail(user.getEmail());
        Optional<User> optionalUserMsidn = userRepository.findUserByMsidn(user.getMsidn());
        Optional<User> optionalUserIdn = userRepository.findUserByIdn(user.getIdn());
        if (optionalUserEmail.isPresent())
            throw new IllegalStateException("Verify details again email");
            if (optionalUserMsidn.isPresent())
                throw new IllegalStateException("Verify details again msidn");
                if (optionalUserIdn.isPresent())
                    throw new IllegalStateException("Verify details again idn");
                    userRepository.save(user);
                    String message = "Customer with id %s successfully created.".formatted(user.getId());
                    return new UserCreationResponseDTO(message);
    }

    public UserDTO getUser(Long id) {
        // Retrieve the user from the UserRepository
        return userRepository.findUserById(id)
                .stream()
                .map(userDTOMapper)
                .findFirst().orElseThrow(() -> new ResourceAccessException(
                        "customer with id [%s] not found".formatted(id)
        ));
    }

    public UserCreationResponseDTO updateUser(Long id, User user) {
        // Retrieve the existing user from the UserRepository
        Optional<User> existingUser = userRepository.findUserById(id);
        if (existingUser.isPresent()) {
            User users = existingUser.get();
            users.setEmail(user.getEmail());
            users.setMsidn(user.getMsidn());
            userRepository.save(users);
            String message = "Customer with id %s successfully updated.".formatted(id);
            return new UserCreationResponseDTO(message);}
        else {
        String message = "Customer with id %s not there.".formatted(id);
        return new UserCreationResponseDTO(message);
    }
    }

    public UserCreationResponseDTO deleteUser(Long id) {
        Optional<User> optionalUserId = userRepository.findUserById(id);
        // Check if the user exists in the UserRepository
        if (optionalUserId.isPresent()){
//             Delete the user using the UserRepository
//            throw new IllegalStateException(String.valueOf(optionalUserId));
            userRepository.deleteById(id);
            String message = "Customer with id %s successfully deleted.".formatted(id);
            return new UserCreationResponseDTO(message);}

        String message = "Customer with id %s not there.".formatted(id);
        return new UserCreationResponseDTO(message);

    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }
}