package ApplicationBalance.dtos.user;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDTO {
    private String name;
    private String email;
    private String password;
}
