package com.Security.Service;


import com.Security.DTO.UserDTO;
import com.Security.Model.User;
import com.Security.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testCreateUser_Success() {
//        // Arrange
//        UserDTO userDto = new UserDTO("testUser", "password123");
//        when(userRepository.findByUserName("testUser")).thenReturn(Optional.empty());
//        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
//
//        // Act
//        assertDoesNotThrow(() -> userService.createUser(userDto));
//
//        // Assert
//        verify(userRepository, times(1)).save(any(User.class));
//    }

    @Test
    public void testCreateUser_UsernameAlreadyExists() {
        // Arrange
        UserDTO userDto = new UserDTO("existingUser", "password123");
        when(userRepository.findByUserName("existingUser")).thenReturn(Optional.of(new User()));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto);
        });

        assertEquals("Username and password cannot be null", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testCreateUser_NullUsername() {
        // Arrange
        UserDTO userDto = new UserDTO(null, "password");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto);
        });

        assertEquals("Username and password cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateUser_DataIntegrityViolation() {
        // Arrange
        UserDTO userDto = new UserDTO("testUser", "password123");
        when(userRepository.findByUserName("testUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        doThrow(new DataIntegrityViolationException("Integrity issue")).when(userRepository).save(any(User.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDto);
        });

        assertEquals("Username and password cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateUser_GenericException() {
        // Arrange
        UserDTO userDto = new UserDTO("testUser", "password123");
        when(userRepository.findByUserName("testUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        doThrow(new RuntimeException("Some error")).when(userRepository).save(any(User.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDto);
        });

        assertEquals("Username and password cannot be null", exception.getMessage());
    }
}
