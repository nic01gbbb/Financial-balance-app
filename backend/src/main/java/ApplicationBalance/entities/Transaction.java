package ApplicationBalance.entities;

import jakarta.persistence.*;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PersistenceUnitTransactionType type; // INCOME or EXPENSE

    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}
