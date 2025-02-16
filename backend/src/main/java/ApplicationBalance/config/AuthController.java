package ApplicationBalance.config;

import ApplicationBalance.dtos.user.UserCreateDTO;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.RoleRepository;
import ApplicationBalance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public String registerUser(@RequestBody UserCreateDTO user) {
        // Encrypt the password before saving
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(roleRepository.findByName(user.getRole()));
        userRepository.save(newUser); // Save the user in the database
        return "User registered successfully!";
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByName(request.getName());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        User user = userOpt.get();

        // Compare the provided password with the stored encrypted password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(request.getName());

        // Return token in the response
        return ResponseEntity.ok(new LoginResponse(token));

    }

    @GetMapping("/test")
    public LoginRequest adminTest(@RequestBody LoginRequest loginRequest) {
        LoginRequest LoginRequest = new LoginRequest();
        LoginRequest.setName(loginRequest.getName());
        LoginRequest.setPassword(loginRequest.getPassword());
        return loginRequest;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUser")
    public List<User> listUsers() {
        // Your logic to return the list of users
        return userRepository.findAll();
    }


}
