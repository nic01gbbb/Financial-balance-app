package ApplicationBalance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "permissions")
@Entity
public class Permission {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private UUID id;

private String name;
private String description;

}
