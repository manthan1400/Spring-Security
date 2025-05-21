package com.Security.DTO;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer userId;
    private String userName;
    private String password;


    public UserDTO(String userName, String password) {
    }
}

