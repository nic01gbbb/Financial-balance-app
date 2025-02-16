package ApplicationBalance.services;

import ApplicationBalance.dtos.role.RoleCreateDTO;
import ApplicationBalance.dtos.role.RoleDTO;
import ApplicationBalance.entities.Permission;
import ApplicationBalance.entities.Role;
import ApplicationBalance.entities.Role_Permission;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.PermissionRepository;
import ApplicationBalance.repositories.RolePermissionRepository;
import ApplicationBalance.repositories.RoleRepository;
import ApplicationBalance.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostConstruct
    public void createAutomatically() {
        List<Role> rules = roleRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();

        if (permissions.isEmpty()) {
            Permission permission1 = new Permission();
            permission1.setName("ADMIN-1");
            permission1.setDescription("Those who belongs to this permission can to change everything");
            permissionRepository.save(permission1);

            Permission permission2 = new Permission();
            permission2.setName("USER-1");
            permission2.setDescription("Those who belongs to this permission can check and purchase available services");
            permissionRepository.save(permission2);

            Permission permission3 = new Permission();
            permission3.setName("USER-2");
            permission3.setDescription("Those who belongs to this permission can check his profits and owes");
            permissionRepository.save(permission3);
            System.out.println("Permissions created successfully");
        }

        if (rules.isEmpty()) {
            List<Permission> myPermissions = putpermission();
            for (Permission p : myPermissions) {
                if (p.getName().startsWith("USER")) {
                    Role role = new Role();
                    role.setName("USER");
                    role.setPermission(p);
                    roleRepository.save(role);
                    Role_Permission rolePermission = new Role_Permission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(p);
                    rolePermissionRepository.save(rolePermission);
                    System.out.println("Role created successfully");
                }
                if (p.getName().startsWith("ADMIN")) {
                    Role role = new Role();
                    role.setName("ADMIN");
                    role.setPermission(p);
                    roleRepository.save(role);
                    Role_Permission rolePermission = new Role_Permission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(p);
                    rolePermissionRepository.save(rolePermission);
                    System.out.println("Role created successfully");
                }


            }
        }
        if (users.isEmpty()) {
            String mypassword = "12345678";
            User firstUser = new User();
            firstUser.setName("Nicholas Garcia");
            firstUser.setEmail("Nicholasfs40@gmail.com");
            firstUser.setPassword(passwordEncoder.encode(mypassword));
            firstUser.setRole(putrole("ADMIN"));
            userRepository.save(firstUser);
            System.out.println("User created successfully");
        } else {
            System.out.println("It's already a role");
        }
    }


    public Role putrole(String rolenames) {
        List<Role> roles = roleRepository.findAll();
        Role rightrole = null;

        for (Role r : roles) {
            if (r.getName().equals(rolenames)) {
                rightrole = r;
            }
        }
        return rightrole;
    }

    public List<Permission> putpermission() {
        List<Permission> permissions = permissionRepository.findAll();
        List<Permission> rightpermission = new ArrayList<>();

        for (Permission permission : permissions) {
            if (permission.getName().startsWith("USER") || permission.getName().startsWith("ADMIN")) {
                rightpermission.add(permission);
            }
        }
        return rightpermission; // Return null if no matching permission is found
    }


    public ResponseEntity<?> createRole(@Valid RoleCreateDTO roleCreateDTO) {
        List<Permission> permissions = permissionRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        List<Role_Permission> RolesPermissoes = rolePermissionRepository.findAll();
        Permission permissionB = null;

        for (Permission p : permissions) {
            if (p.getName().equals(roleCreateDTO.getPermission())) {
                permissionB = p;

            }
        }


        if (permissionB == null) {
            return ResponseEntity.badRequest().body("Permission not found:" + roleCreateDTO.getPermission());
        }


        for (Role_Permission rp : RolesPermissoes) {
            if (rp.getRole().getName().equals(roleCreateDTO.getName()) &&
                    rp.getPermission().getId().equals(permissionB.getId())
            )
                return ResponseEntity.badRequest().body("This role already possessed this permission:" + roleCreateDTO.getPermission());
        }

        for (Role r : roles) {
            if (r.getName().equals(roleCreateDTO.getName())) {
                Role newRole = new Role();
                newRole.setName(r.getName());
                newRole.setPermission(r.getPermission());
                RoleDTO roleDTO = TurnOnRoleDTO(newRole);
                Role_Permission rolePermission = new Role_Permission();
                rolePermission.setPermission(permissionB);
                rolePermission.setRole(r);
                rolePermissionRepository.save(rolePermission);
                return ResponseEntity.status(HttpStatus.CREATED).body(roleDTO);
            }
        }


        for (Role r : roles) {
            if (!r.getName().equals(roleCreateDTO.getName())) {
                Role newRole = new Role();
                newRole.setName(roleCreateDTO.getName());
                newRole.setPermission(permissionB);
                roleRepository.save(newRole);
                RoleDTO roleDTO = TurnOnRoleDTO(newRole);
                Role_Permission rolePermission = new Role_Permission();
                rolePermission.setPermission(permissionB);
                rolePermission.setRole(newRole);
                rolePermissionRepository.save(rolePermission);
                return ResponseEntity.status(HttpStatus.CREATED).body(roleDTO);

            }
        }
        return null;
    }


    public RoleDTO TurnOnRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setPermission_id(role.getPermission());
        return roleDTO;

    }

}
