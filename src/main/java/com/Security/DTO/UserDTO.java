package com.Security.DTO;




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
    public void setId(Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

