package theo.bank.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {

    String name;
    String email;
    String phone;
    int age;
    String password;
    String transactionPin;
}
