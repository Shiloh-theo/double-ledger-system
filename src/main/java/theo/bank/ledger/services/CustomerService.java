package theo.bank.ledger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import theo.bank.ledger.dto.CreatedAccountDto;
import theo.bank.ledger.dto.CustomerDto;
//import theo.bank.ledger.dto.createdAccountDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.models.Customers;
import theo.bank.ledger.repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    public ResponseEntity<CreatedAccountDto> register(CustomerDto dto) {

        Customers newCustomer = new Customers();
        CreatedAccountDto createdAccountDto = new CreatedAccountDto();

        newCustomer.setAge(dto.getAge());
        newCustomer.setName(dto.getName());
        newCustomer.setPhone(dto.getPhone());
        newCustomer.setEmail(dto.getEmail());

        Customers savedCustomer = customerRepository.save(newCustomer);

        Accounts newAccount = accountService.createAccount(dto, savedCustomer);

        createdAccountDto.setName(savedCustomer.getName());
        createdAccountDto.setAge(savedCustomer.getAge());
        createdAccountDto.setAccountNo(newAccount.getAccountNo());
        createdAccountDto.setBalance(newAccount.getBalance());
        createdAccountDto.setEmail(savedCustomer.getEmail());
        createdAccountDto.setPhone(savedCustomer.getPhone());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdAccountDto);

    }

    public Customers getCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customers updateCustomer(Customers customer) {
        return customerRepository.save(customer);
    }


}
