package ApplicationBalance.repositories;

import ApplicationBalance.entities.Role_Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<Role_Permission, UUID> {
}
