package theo.bank.ledger.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    private LocalDate date;
    private LocalTime time;

    private String senderAccount;
    private String receiverAccount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;    // BigDecimal — not double
}