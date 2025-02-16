package ApplicationBalance.dtos.role;
import ApplicationBalance.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private UUID id;
    private String name;
    private Permission permission_id;
}
