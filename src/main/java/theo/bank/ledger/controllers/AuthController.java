package theo.bank.ledger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import theo.bank.ledger.dto.TransactionDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.repositories.AccountRepository;
import theo.bank.ledger.security.JwtUtil;
import theo.bank.ledger.dto.LoginDto;

@RestController
@RequestMapping("bank")
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        Accounts account = accountRepository.findByEmail(dto.getEmail());

        if (account == null || !account.getPassword().equals(dto.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dto.getEmail());
        return ResponseEntity.ok(token);
    }
}