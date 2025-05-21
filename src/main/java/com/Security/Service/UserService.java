package com.Security.Service;

import com.Security.DTO.UserDTO;
import com.Security.Model.User;
import com.Security.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(UserDTO userDto) {
        String userName = userDto.getUserName();
        String password = userDto.getPassword();

        if (userDto.getUserName() == null || userDto.getPassword() == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }


        try {
            logger.info("Attempting to create user with username: {}", userName);
            // Check if user already exists
            if (userRepository.findByUserName(userName).isPresent()) {
                logger.warn("User already exists with username: {}", userName);
                throw new IllegalArgumentException("Username already taken");
            }

            // Create a new user
            User user = new User();
            user.setUserName(userName);
            user.setPassword(passwordEncoder.encode(password)); // Hash the password

            // Save user to the repository
            userRepository.save(user);
            logger.info("User created successfully: {}", userName);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            throw e;  // Rethrow to inform the caller of the validation error
        }
        catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation: {}", e.getMessage(), e);
            throw new RuntimeException("User creation failed due to data integrity violation");
        }catch (Exception e) {
            logger.error("An error occurred while creating user: {}", e.getMessage(), e);
            throw new RuntimeException("User creation failed");  // Rethrow with a generic message
        }
    }

    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser =userRepository.findByUserName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                new ArrayList<>());
    }
}
