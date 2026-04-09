package theo.bank.ledger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import theo.bank.ledger.dto.TransactionDto;
import theo.bank.ledger.dto.TransactionHistoryDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.security.JwtUtil;
import theo.bank.ledger.services.AccountService;
import theo.bank.ledger.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("bank")
public class TransactionController {

    @Autowired
    TransactionService service;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AccountService accountService;

    @PostMapping("transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionDto dto) {
        return service.transfer(dto);
    }

    @GetMapping("transactions")
    public ResponseEntity<List<TransactionHistoryDto>> getTransactions(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        Accounts account = accountService.getAccountDetails(email);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        List<TransactionHistoryDto> history = service.getTransactionHistory(account.getAccountNo());
        return ResponseEntity.ok(history);
    }
}