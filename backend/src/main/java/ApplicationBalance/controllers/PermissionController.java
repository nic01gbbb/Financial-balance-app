package ApplicationBalance.controllers;

import ApplicationBalance.dtos.permission.PermissionCreateDTO;
import ApplicationBalance.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {


    @Autowired
    PermissionService permissionService;

    @PostMapping("/create")
    public ResponseEntity<?> createPermission(@Validated @RequestBody PermissionCreateDTO permissionCreateDTO) {
        try {
            return permissionService.createPermission(permissionCreateDTO);
        } catch (Exception e) {
            ResponseEntity.ok(e.getMessage());
            return null;
        }
    }


}
