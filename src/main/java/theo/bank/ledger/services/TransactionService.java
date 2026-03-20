package theo.bank.ledger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import theo.bank.ledger.dto.TransactionDto;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.models.Transactions;
import theo.bank.ledger.repositories.AccountRepository;
import theo.bank.ledger.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    AccountRepository accountRepository;

//    @Autowired
//    Accounts accounts;

    public ResponseEntity<Transactions> credit(TransactionDto dto) {

        Transactions transactions = new Transactions();
//        transactions.setDate();
//        transactions.setTime();
        transactions.setReceiverAccount(dto.getCreditAccount());
        transactions.setSenderAccount(dto.getDebitAccount());
        transactions.setAmount(dto.getAmount());

        repository.save(transactions);

        Accounts accounts = accountRepository.findByAccountNo(dto.getCreditAccount());

        accounts.setBalance(accounts.getBalance() + dto.getAmount());

        return ResponseEntity.status(HttpStatus.CREATED).body(transactions);
    }


    public ResponseEntity<Transactions> debit(TransactionDto dto) {

        Transactions transactions = new Transactions();
        transactions.setSenderAccount(dto.getDebitAccount());
        transactions.setReceiverAccount(dto.getCreditAccount());
        transactions.setAmount(dto.getAmount());

        repository.save(transactions);

        Accounts debitAccount = accountRepository.findByAccountNo(dto.getDebitAccount());
        if (debitAccount.getBalance() < dto.getAmount()){
            throw new RuntimeException("Insufficient balance");
        }

        debitAccount.setBalance(debitAccount.getBalance() - dto.getAmount());

        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    public ResponseEntity<Transactions> transact(TransactionDto dto) {
        Transactions transactions = new Transactions();
        transactions.setReceiverAccount(dto.getCreditAccount());
        transactions.setSenderAccount(dto.getDebitAccount());
        transactions.setAmount(dto.getAmount());

        repository.save(transactions);

        Accounts debitAccount = accountRepository.findByAccountNo(dto.getDebitAccount());
        Accounts creditAccount = accountRepository.findByAccountNo(dto.getCreditAccount());

        return null;
    }
}
