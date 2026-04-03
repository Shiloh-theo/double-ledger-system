package theo.bank.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedAccountDto {

    String name;
    String email;
    String phone;
    int age;
    String password;
    String transactionPin;
    String accountNo;
    BigDecimal balance;
    int CustomerId;

}
