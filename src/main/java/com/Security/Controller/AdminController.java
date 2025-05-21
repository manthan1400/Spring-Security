    package com.Security.Controller;

    import com.Security.DTO.UserDTO;
    import com.Security.Service.UserService;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/admin")
    @Slf4j
    public class AdminController {

        private final UserService userService;

        public AdminController(UserService userService) {
            this.userService = userService;
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody @Validated UserDTO userDto) {
            try {

                userService.createUser(userDto);
                log.info("User registered successfully: {}", userDto.getUserName());
                return ResponseEntity.ok("User registered successfully");
            } catch (IllegalArgumentException e) {
                log.warn("Registration failed: {}", e.getMessage());
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            catch (Exception e) {
                log.error("An error occurred during user registration: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred: " + e.getMessage());
            }
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/deleteAll")
        public ResponseEntity<String> deleteAllUsers() {
            try {
                userService.deleteAllUsers();
                log.info("All users deleted successfully.");
                return ResponseEntity.ok("All users deleted successfully.");
            } catch (Exception e) {
                // Handle the exception properly
                log.error("An error occurred during user deletion: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred: " + e.getMessage());
            }

        }
    }
