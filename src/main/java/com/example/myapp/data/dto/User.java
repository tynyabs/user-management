package com.example.myapp.data.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private int msidn;
    private String idn;

    public User() {
    }

    public User(Long id, String name, String surname, String email, int msidn, String idn) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.msidn = msidn;
        this.idn = idn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMsidn() {
        return msidn;
    }

    public void setMsidn(int msidn) {
        this.msidn = msidn;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    }
