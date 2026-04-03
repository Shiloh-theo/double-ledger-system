package theo.bank.ledger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theo.bank.ledger.dto.CustomerDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.models.Customers;
import theo.bank.ledger.repositories.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;



    public Accounts createAccount(CustomerDto dto, Customers newCustomer) {
        String phone = dto.getPhone().substring(1);

        Accounts account = new Accounts();
                account.setAccountNo(phone);
                account.setBalance(BigDecimal.ZERO);
                account.setPassword(dto.getPassword());
                account.setCustomerId(newCustomer.getCustomerId());
                account.setEmail(dto.getEmail());

        return repository.save(account);
    }


    public Accounts getAccountDetails(String email) {
        return repository.findByEmail(email);
    }

    public Accounts updateAccount(Accounts account) {
        return repository.save(account);
    }
}
