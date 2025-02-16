package ApplicationBalance.services;


import ApplicationBalance.dtos.permission.PermissionCreateDTO;
import ApplicationBalance.dtos.permission.PermissionDTO;
import ApplicationBalance.entities.Permission;
import ApplicationBalance.repositories.PermissionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {


    @Autowired
    private PermissionRepository permissionRepository;

    public ResponseEntity<?> createPermission(@Valid PermissionCreateDTO permissionCreateDTO) {
        List<Permission> allpermissions = permissionRepository.findAll();
        for (Permission permission : allpermissions) {
            if (permission.getName().equals(permissionCreateDTO.getName())) {
                return ResponseEntity.badRequest().body("This permission name already exists:"
                        + permissionCreateDTO.getName());

            } else if (permission.getDescription().equals(permissionCreateDTO.getDescription())) {
                return ResponseEntity.badRequest().body("This permission description already exists:"
                        + permissionCreateDTO.getName());
            }
        }

        Permission newpermission = new Permission();
        newpermission.setName(permissionCreateDTO.getName());
        newpermission.setDescription(permissionCreateDTO.getDescription());
        permissionRepository.save(newpermission);
        PermissionDTO permissionDTO = TurnOnPermissionDTO(newpermission);
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionDTO);

    }

    public PermissionDTO TurnOnPermissionDTO(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setName(permission.getName());
        permissionDTO.setDescription(permission.getDescription());
        return permissionDTO;
    }


}
