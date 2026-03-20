package theo.bank.ledger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import theo.bank.ledger.dto.CreatedAccountDto;
import theo.bank.ledger.dto.CustomerDto;
import theo.bank.ledger.models.Customers;
import theo.bank.ledger.services.CustomerService;

@RestController
@RequestMapping("bank")
public class CustomerController {

    @Autowired
    CustomerService service;

    @PostMapping("register")
    public ResponseEntity<CreatedAccountDto> register(@RequestBody CustomerDto dto){
        return service.register(dto);
    }

    @GetMapping("getCustomer")
    public Customers getCustomer(@RequestParam String email){
        return service.getCustomer(email);
    }

    @PutMapping("updateCustomer")
    public Customers updateCustomer(@RequestBody Customers customer){
        return service.updateCustomer(customer);
    }
}
