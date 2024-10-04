package com.Security.DTO;


import org.springframework.validation.annotation.Validated;

@Validated
public class UserDTO {

    private Integer userId;

    private String userName;
    private String password;
    // Default constructor
    public UserDTO() {}

    // Parameterized constructor
    public UserDTO(Integer userId, String userName, String password) {
        this.userId = userId;


        this.userName = userName;
        this.password = password;
    }

    public UserDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getters and Setters
    public Integer getId() {
        return userId;
    }
    public void setId(Integer id) {
        this.userId = id;
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

