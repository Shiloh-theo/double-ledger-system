package theo.bank.ledger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theo.bank.ledger.dto.CustomerDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.models.Customers;
import theo.bank.ledger.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;

    Accounts account = new Accounts();

    public Accounts createAccount(CustomerDto dto, Customers newCustomer) {
        StringBuilder sb = new StringBuilder(dto.getPhone());
        String phone = sb.deleteCharAt(0).toString();

                account.setAccountNo(phone);
                account.setBalance(0.00);
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
