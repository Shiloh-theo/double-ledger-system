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
    Integer age;
    String password;
    String transactionPin;
}
