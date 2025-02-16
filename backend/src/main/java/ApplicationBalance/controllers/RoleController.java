package ApplicationBalance.controllers;


import ApplicationBalance.dtos.role.RoleCreateDTO;
import ApplicationBalance.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Valid

public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/create")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreateDTO roleCreateDTO) {
        try {
            return roleService.createRole(roleCreateDTO);
        } catch (Exception e) {
            ResponseEntity.ok(e.getMessage());
            return null;
        }
    }
}
