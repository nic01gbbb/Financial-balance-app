package ApplicationBalance.controllers;

import ApplicationBalance.dtos.user.UserCreateDTO;
import ApplicationBalance.dtos.user.UserDTO;
import ApplicationBalance.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            return userService.createUser(userCreateDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Get all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/listusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
