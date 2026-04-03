package theo.bank.ledger.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(unique = true, nullable = false)
    private String accountNo;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    private String password;

    private int customerId;

    private String email;

    @Version
    private Long version;
}
