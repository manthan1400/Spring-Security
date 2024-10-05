package com.Security.Controller;

import com.Security.DTO.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import com.Security.Model.User;
import com.Security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Validated
public class BasicController {

    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @GetMapping("/get")
    public ResponseEntity<String> getMessage(){
        return ResponseEntity.ok("Rest API Working");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UserDTO userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        logger.info("Login attempt for user: {}", user.getUserName());

        if (user.getUserName() == null || user.getUserName().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new LoginResponse("Username and password must not be empty"));
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            return ResponseEntity.ok(new LoginResponse("User authenticated successfully"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("An error occurred: " + e.getMessage()));
        }
    }

    // get method
    @GetMapping("/user/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable("name") String userName) {
        Optional<User> userOptional = userService.getUserByUserName(userName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(new UserDTO(user.getUserName(), user.getPassword())); // Use DTO for response
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }
    }

    public static class LoginResponse {
        private String message;

        public LoginResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
