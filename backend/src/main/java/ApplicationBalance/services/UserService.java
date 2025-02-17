package ApplicationBalance.services;

import ApplicationBalance.dtos.user.UserCreateDTO;
import ApplicationBalance.dtos.user.UserDTO;
import ApplicationBalance.entities.Role;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.RoleRepository;
import ApplicationBalance.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Validated
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;



    // Create a new user
    public ResponseEntity<?> createUser(@Valid UserCreateDTO userCreateDTO) {
        Role role_id = null;
        List<Role> roles = roleRepository.findAll();
        List<User> allusers = userRepository.findAll();
        List<User> ADM = allusers.stream().filter(e ->
                e.getRole().getName().equals("ADMIN")).toList();
        int SizeADM = ADM.size();


        for (Role role : roles) {
            if (role.getName().equals(userCreateDTO.getRole())) {
             System.out.println("I found");
                role_id = role;
            }
        }


        // Check if the email already exists
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        } else if (SizeADM == 2 && userCreateDTO.getRole().equals("ADMIN")) {
            return ResponseEntity.badRequest().body("The role needs to be user, because already exists the administrators ones");
        } else if (!userCreateDTO.getRole().equals("ADMIN") && !userCreateDTO.getRole().equals("USER")) {
            return ResponseEntity.badRequest().body("The role to be chosen needs to be user or administrator");
        }


        // Create new user entity
        User newUser = new User();
        newUser.setName(userCreateDTO.getName());
        newUser.setEmail(userCreateDTO.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(userCreateDTO.getPassword()));
        newUser.setRole(role_id); // You can set the default role as USER

        // Save the user in the database
        userRepository.save(newUser);

        // Convert the entity to UserDTO and return
        UserDTO userDTO = convertToUserDTO(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

    }

    // Helper method to convert Users entity to UserDTO
    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Map entity list to DTO list
        return users.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new UserDTO(foundUser.getId(), foundUser.getName(), foundUser.getEmail(), foundUser.getRole());
        }
        return null; // Return null if the user isn't found (controller handles this)
    }


}

