package com.Security.Model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    @Column(name = "userID")
    private Integer userId;


    @Column(name = "username", nullable = false) // Maps to the USERNAME column
    private String userName;


    @Column(name = "password", nullable = false) // Maps to the PASSWORD column
    private String password;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(Integer userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    // Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId; // Use 'this' to refer to the class field
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

