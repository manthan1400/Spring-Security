package com.Security.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    @Column(name = "userID")
    private Integer userId;
    @Column(name = "username", nullable = false) // Maps to the USERNAME column
    private String userName;
    @Column(name = "password", nullable = false) // Maps to the PASSWORD column
    private String password;

}

