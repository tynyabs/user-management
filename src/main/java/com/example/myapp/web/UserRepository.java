package com.example.myapp.web;

import com.example.myapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    User findUserByUsername(String username) throws UsernameNotFoundException;
    Optional<User> findUserByMsidn(String msidn);
    Optional<User> findUserByIdn(String idn);
    Optional<User> findUserById(Long id);
    void deleteById(Long id);

}