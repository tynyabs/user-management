package com.example.myapp.service;

import com.example.myapp.Security.config.Tokens;
import com.example.myapp.dto.*;
import com.example.myapp.web.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Tokens tokens;
    private final UserMapper userMapper;
    public UserCreationResponse createUser(UserCreate userCreate) {
        Optional<com.example.myapp.entity.User> optionalUserEmail = userRepository.findUserByEmail(userCreate.getEmail());
        Optional<com.example.myapp.entity.User> optionalUserMsidn = userRepository.findUserByMsidn(userCreate.getMsidn());
        Optional<com.example.myapp.entity.User> optionalUserIdn = userRepository.findUserByIdn(userCreate.getIdn());
        String emailtaken = "email taken";
        String idntaken = "idn taken";
        String msidntaken = "msidn taken";
        if (optionalUserEmail.isPresent())
            return new UserCreationResponse(emailtaken);
        if (optionalUserMsidn.isPresent())
            return new UserCreationResponse(msidntaken);
        if (optionalUserIdn.isPresent())
            return new UserCreationResponse(idntaken);
        com.example.myapp.entity.User user = com.example.myapp.entity.User.
                build(0L, userCreate.getName(), userCreate.getSurname(),
                        userCreate.getEmail(), userCreate.getMsidn(),
                        userCreate.getIdn(), userCreate.getPassword(),
                        userCreate.getUsername(), userCreate.getUserRole());
        userRepository.save(user);
        String message = "Customer with name %s successfully created.".formatted(user.getName());
        return new UserCreationResponse(message);
    }

    public User getUser(Long id) {
        // Retrieve the user from the UserRepository
        return userRepository.findUserById(id)
                .stream()
                .map(userMapper)
                .findFirst().orElseThrow(() -> new IllegalStateException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public UserCreationResponse updateUser(Long id, UserUpdate userUpdate) {
        // Retrieve the existing user from the UserRepository
        Optional<com.example.myapp.entity.User> existingUser = userRepository.findUserById(id);
        com.example.myapp.entity.User user = null;
        if (existingUser.isPresent()) {
            user = existingUser.get();

            // Check if the email is being updated
            if (userUpdate.getEmail() != null && !userUpdate.getEmail().equals(user.getEmail())) {
                // Check if the updated email already exists in the database
                Optional<com.example.myapp.entity.User> existingEmailUser = userRepository.findUserByEmail(userUpdate.getEmail());
                if (existingEmailUser.isPresent()) {
                    String errorMessage = "Email taken";
                    return new UserCreationResponse(errorMessage);
                }
                user.setEmail(userUpdate.getEmail());
            }

            // Check if msidn is being updated
            if (userUpdate.getMsidn() != null && !userUpdate.getMsidn().equals(user.getMsidn())) {
                // Check if the updated phone number already exists in the database
                Optional<com.example.myapp.entity.User> existingMsidnUser = userRepository.findUserByMsidn(userUpdate.getMsidn());
                if (existingMsidnUser.isPresent()) {
                    String errorMessage = "msidn taken";
                    return new UserCreationResponse(errorMessage);
                }
                user.setMsidn(userUpdate.getMsidn());
            }

            // Save the updated user
            userRepository.save(user);
            String message = "User with id %s successfully updated.".formatted(id);
            return new UserCreationResponse(message);
        }

        String message = "Customer with id %s not there.".formatted(id);
        return new UserCreationResponse(message);
    }

    public UserCreationResponse deleteUser(Long id) {
        Optional<com.example.myapp.entity.User> optionalUserId = userRepository.findUserById(id);
        // Check if the user exists in the UserRepository
        if (optionalUserId.isPresent()) {
            userRepository.deleteById(id);
            String message = "Customer with id %s successfully deleted.".formatted(id);
            return new UserCreationResponse(message);
        }

        String message = "Customer with id %s not there.".formatted(id);
        return new UserCreationResponse(message);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.myapp.entity.User user = userRepository.findUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

//    public String loginService(UserLoginRequests userLoginRequests){
//        String username = userLoginRequests.getUsername();
//        String password = userLoginRequests.getPassword();
//        return password;
//    }
    public ResponseEntity<UserLoginResponse> loginService(String username, String password)  {

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            com.example.myapp.entity.User user = userRepository.findUserByUsername(username);


            Instant instant = Instant.now();
            Instant expiryIso = instant.plus(1, ChronoUnit.HOURS);
            Integer expiryTime = 3600000; //1 Hour (Should be placed in config)
            Date expiry = new Date(System.currentTimeMillis() + expiryTime);
            Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
            String token = tokens.getJWTToken(username, expiry,
                    loadUserByUsername(username).getAuthorities().toString());
            UserLoginResponse response = new UserLoginResponse();
            response.setRole(loadUserByUsername(username).getAuthorities().toString());
            response.setToken(token);
            response.setExpiry(DateTimeFormatter.ISO_INSTANT.format(expiryIso));
            response.setIssuedAt(DateTimeFormatter.ISO_INSTANT.format(issuedAt));

            if(user != null && passwordEncoder.matches(password, user.getPassword())) {

                return  ResponseEntity.ok(response);
            }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}