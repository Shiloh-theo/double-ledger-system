package theo.bank.ledger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import theo.bank.ledger.dto.TransactionDto;
import theo.bank.ledger.services.TransactionService;

@RestController
@RequestMapping("bank")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping("transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionDto dto) {
        return service.transfer(dto);
    }
}