package com.example.myapp.web;

import com.example.myapp.data.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByMsidn(int msidn);

    Optional<User> findUserByIdn(String idn);

    Optional<User> findUserById(Long id);
    void deleteById(Long id);

}