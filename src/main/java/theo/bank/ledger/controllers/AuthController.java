package theo.bank.ledger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        Accounts account = accountRepository.findByEmail(dto.getEmail());

        if (account == null || !passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dto.getEmail());
        return ResponseEntity.ok(token);
    }
}
