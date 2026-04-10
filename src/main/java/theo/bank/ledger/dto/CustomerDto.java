package theo.bank.ledger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // Fix: Jackson needs a no-args constructor to deserialize JSON into this object
public class CustomerDto {

    String name;
    String email;
    String phone;
    Integer age;
    String password;
    String transactionPin;
}
