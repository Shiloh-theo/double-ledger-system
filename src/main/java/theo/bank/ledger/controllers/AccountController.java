package theo.bank.ledger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import theo.bank.ledger.dto.CustomerDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.models.Customers;
import theo.bank.ledger.security.JwtUtil;
import theo.bank.ledger.services.AccountService;

@RestController
@RequestMapping(path = "bank")
public class AccountController {

    @Autowired
    AccountService service;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("account")
    public ResponseEntity<Accounts> getAccount(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        Accounts account = service.getAccountDetails(email);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(account);
    }

    @PostMapping("createAccount")
    public Accounts createAccount(@RequestBody CustomerDto dto, Customers customer){
        return service.createAccount(dto, customer);
    }

    @GetMapping("getAccountDetails")
    public Accounts getAccountDetails (@RequestParam String email){
        return service.getAccountDetails(email);
    }

    @PutMapping("updateAccount")
    public Accounts updateAccount (@RequestBody Accounts account){
        return service.updateAccount(account);
    }
}
