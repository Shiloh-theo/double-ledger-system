package theo.bank.ledger.controllers;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import theo.bank.ledger.dto.TransactionDto;
import theo.bank.ledger.models.Transactions;
import theo.bank.ledger.services.TransactionService;

@RestController
@RequestMapping("bank")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping("credit")
    public ResponseEntity<Transactions> credit(@RequestParam TransactionDto dto){
        return service.credit(dto);
    }

    @PostMapping("debit")
    public ResponseEntity<Transactions> debit(@RequestParam TransactionDto dto){
        return service.debit(dto);
    }

    @PostMapping("transact")
    public ResponseEntity<Transactions> transact(@RequestParam TransactionDto dto){
        return service.transact(dto);
    }
}
