package ApplicationBalance.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profits")
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who ordered the service

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service; // The service that generated the profit


    private BigDecimal amount;
    private LocalDateTime createdAt;

    // Getters and Setters
}
