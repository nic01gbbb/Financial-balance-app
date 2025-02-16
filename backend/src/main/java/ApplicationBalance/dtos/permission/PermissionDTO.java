package ApplicationBalance.dtos.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {
    private UUID id;
    private String name;
    private String description;
}
